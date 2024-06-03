package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

abstract class Minion(player: Player, position: Vector2) extends Entity(player, position) with Deployable {

  val moveSpeed: Int

  var path: List[(Int, Int)] = _

  def move(deltaTime: Float): Unit = {
    if (path.length != 0) {
      val index: Int = (deltaTime*moveSpeed).toInt
      val newPosition: Vector2 = new Vector2(path(index)._1, path((index))._2)
      this.position = newPosition
    }
  }
}