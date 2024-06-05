package clash_royal_ISC.entities.minions

import clash_royal_ISC.{GameWindow, Player}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.math.{Interpolation, Vector2}

abstract class Minion(player: Player) extends Entity(player) with Deployable {

  val moveSpeed: Float
  var path: List[(Int, Int)] = _

  var currentFrame: Int = 0
  var nFrames: Int = 1
  var dt: Float = 0
  var lastPosition: Vector2 = _
  var newPosition: Vector2 = _

  override def spawn(position: Vector2): Unit = {
    super.spawn(position)
    this.lastPosition = position
    this.newPosition = position
  }

  //  def move(deltaTime: Float): Unit = {
  //    val frameTime: Float = GameWindow.FRAME_TIME / moveSpeed
  //
  //    println(s"Frame time $frameTime")
  //    println(s"Current frame $currentFrame")
  //
  //    this.position = this.lastPosition
  //    if (!this.targetIsInRange()) {
  //      this.dt += deltaTime
  //      val alpha: Float = (this.dt + frameTime * this.currentFrame) / (frameTime * this.nFrames)
  //      println(s"Alpha $alpha")
  //
  //
  //      if(this.path.nonEmpty){
  //        val pathHeadPosition: Vector2 = new Vector2(this.path.head._1, this.path.head._2)
  //        this.position.interpolate(pathHeadPosition, alpha, Interpolation.linear)
  //        this.newPosition = position
  //          if(this.position.dst(this.target.position) <= pathHeadPosition.dst(this.target.position)){
  //            this.path = this.path.tail

  //        }
  //      }
  //    } else {
  //      this.dt = 0
  //    }
  //
  //    if (this.dt > frameTime) {
  //      this.dt -= frameTime
  //      this.currentFrame = (this.currentFrame + 1) % this.nFrames

  //      if (this.currentFrame == 0) {
  //
  //        this.lastPosition = new Vector2(newPosition)
  //        this.position = new Vector2(this.newPosition)
  //      }
  //    }
  def move(deltaTime: Float): Unit = {
    if (path == null || path.isEmpty || this.targetIsInRange()) return

    val frameTime: Float = GameWindow.FRAME_TIME / moveSpeed

    this.dt += deltaTime

    if (this.dt > frameTime) {
      print("Position updated")
      this.dt -= frameTime
      val nextPosition = new Vector2(path.head._1, path.head._2)

      this.lastPosition = this.newPosition
      this.newPosition = nextPosition
      path = path.tail
    }

    val alpha: Float = this.dt / frameTime
    this.position = this.lastPosition.interpolate(this.newPosition, alpha, Interpolation.linear)

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