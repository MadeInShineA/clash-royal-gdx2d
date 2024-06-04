package clash_royal_ISC.entities.minions

import clash_royal_ISC.{GameWindow, Player}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.math.{Interpolation, Vector2}

abstract class Minion(player: Player) extends Entity(player) with Deployable {

  val moveSpeed: Int
  var path: List[(Int, Int)] = _

  var currentFrame: Int = 0
  var nFrames: Int = 4
  var dt: Float = 0
  var lastPosition: Vector2 = this.position
  var newPosition: Vector2 = this.position

  def move(deltaTime: Float): Unit = {
    if (path.length != 0) {
      val index: Int = (deltaTime*moveSpeed).toInt
      val newPosition: Vector2 = new Vector2(path(index)._1, path((index))._2)
      this.position = newPosition
    }

    if (this.dt > frameTime) {
      this.dt -= frameTime
      this.currentFrame = (this.currentFrame + 1) % this.nFrames

      if (this.currentFrame == 0) {
        this.lastPosition = new Vector2(newPosition)
        this.position = new Vector2(this.newPosition)
      }
    }
  }

  def setPath(): Unit = {
//    println("Entity position : " + this.position)
//    println("Target position : " + this.target.position)
    this.path = AStar.findPath((this.position.x.toInt, this.position.y.toInt), (this.target.position.x.toInt, this.target.position.y.toInt))
    println("Found path : " + this.path.mkString(","))
  }
}