package clash_royal_ISC.entities.minions

import clash_royal_ISC.{GameWindow, Player}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.math.Vector2

abstract class Minion(player: Player) extends Entity(player) with Deployable {

  val moveSpeed: Int
  var path: List[(Int, Int)] = _

  def move(deltaTime: Float): Unit = {
    if (path.length != 0) {
      val index: Int = (deltaTime*moveSpeed).toInt
      val newPosition: Vector2 = new Vector2(path(index)._1, path((index))._2)
      this.position = newPosition
    }
  }

  def setPath(): Unit = {
    println("Entity position : " + this.position)
    println("Target position : " + this.target.position)
    this.path = AStar.findPath((this.position.x.toInt, this.position.y.toInt + GameWindow.CAMERA_OFFSET), (this.target.position.x.toInt, this.target.position.y.toInt + GameWindow.CAMERA_OFFSET))
    println("Found path : " + this.path.mkString(","))
  }
}