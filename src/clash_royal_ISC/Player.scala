package clash_royal_ISC

import clash_royal_ISC.Player.{P1_TOWER_POSITION, P2_TOWER_POSITION, playersArray}
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.buildings.Tower
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Player() {

  var hand: Hand = new Hand(this)
  var currentElixir: Double = 0.0

  playersArray += this

  assert(playersArray.length <= 2)

  val tower: Tower = new Tower(this)
  tower.spawn(if(playersArray.length == 1) P1_TOWER_POSITION else P2_TOWER_POSITION)

  def deployEntity(entity: Entity with Deployable, position: Vector2): Unit = {
    if (entity.cost <= currentElixir) {
      this.currentElixir -= entity.cost
      this.hand.removeEntity(entity)
      entity.spawn(position)
      this.hand.addEntity()
    }
  }
}

object Player {
  val playersArray: ArrayBuffer[Player] = new ArrayBuffer()

  val P1_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 230)
  val P2_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 800)

}
