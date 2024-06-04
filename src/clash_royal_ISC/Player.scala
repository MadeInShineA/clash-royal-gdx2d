package clash_royal_ISC

import clash_royal_ISC.Player.playersArray
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.buildings.Tower
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Player() {

  val P1_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 230)
  val P2_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 800)

  var hand: ArrayBuffer[Entity] = _
  var currentElixir: Double = 0.0

  playersArray += this

  assert(playersArray.length <= 2)

  val tower: Tower = new Tower(this)
  tower.spawn(if(playersArray.length == 1) P1_TOWER_POSITION else P2_TOWER_POSITION)

  def deployEntity(entity: Entity with Deployable, position: Vector2): Unit = {
    if (entity.cost <= currentElixir) {
      entity.spawn(position)
      this.currentElixir -= entity.cost
      this.addEntityToHand()
    }
  }

  def addEntityToHand(): Unit = {
    //fonction qui remet une carte dans la main
  }
}

object Player {
  val playersArray: ArrayBuffer[Player] = new ArrayBuffer()

}
