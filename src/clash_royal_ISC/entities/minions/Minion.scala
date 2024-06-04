package clash_royal_ISC.entities.minions

import clash_royal_ISC.{GameWindow, Player}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.math.{Interpolation, Vector2}

abstract class Minion(player: Player) extends Entity(player) with Deployable {

  val moveSpeed: Float
  var path: List[(Int, Int)] = _

  var currentFrame: Int = 0
  var nFrames: Int = 4
  var dt: Float = 0
  var lastPosition: Vector2 = _
  var newPosition: Vector2 = _

  override def spawn(position: Vector2): Unit = {
    super.spawn(position)
    this.lastPosition = position
    this.newPosition = position
  }

  def move(deltaTime: Float): Unit = {
    val frameTime: Float = GameWindow.FRAME_TIME / moveSpeed

    println(s"Frame time $frameTime")
    println(s"Current frame $currentFrame")

    this.position = this.lastPosition
    if (!this.targetIsInRange()) {
      dt += deltaTime
      val alpha: Float = (dt + frameTime * currentFrame) / (frameTime * nFrames)
      println(s"Alpha $alpha")

      this.position.interpolate(new Vector2(this.path.head._1, this.path.head._2), alpha, Interpolation.linear)
      this.newPosition = position
    } else {
      this.dt = 0
    }

    if (this.dt > frameTime) {
      println("caca")
      this.dt -= frameTime
      this.currentFrame = (this.currentFrame + 1) % this.nFrames

      if (this.currentFrame == 0) {

        this.lastPosition = new Vector2(newPosition)
        this.position = new Vector2(this.newPosition)
      }
    }
  }

//  def move(deltaTime: Float): Unit = {
//    if (path.length > this.moveSpeed ) {
//      val index: Int = moveSpeed
//      val newPosition: Vector2 = new Vector2(path(index)._1, path((index))._2)
//      this.position = newPosition
//    }else if(path.nonEmpty){
//      val newPosition: Vector2 = new Vector2(path.last._1, path.last._2)
//      this.position = newPosition
//    }
//  }

  def setPath(): Unit = {
    this.path = AStar.findPath((this.position.x.toInt, this.position.y.toInt), (this.target.position.x.toInt, this.target.position.y.toInt))
  }
}