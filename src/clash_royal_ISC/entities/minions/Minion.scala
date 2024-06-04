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
    val frameTime: Float = GameWindow.FRAME_TIME / moveSpeed

    this.position = new Vector2(this.lastPosition)
    if (!this.targetIsInRange()) {
      dt += deltaTime
      val alpha: Float = (dt + frameTime * currentFrame) / (frameTime * nFrames)

      this.position.interpolate(new Vector2(this.path.last._1, this.path.last._2), alpha, Interpolation.linear)
    } else {
      this.dt = 0
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