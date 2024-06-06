package clash_royal_ISC

import clash_royal_ISC.Player.{P1_TOWER_POSITION, P2_TOWER_POSITION, playersArray}
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.buildings.Tower
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Player private {

  var hand: Hand = new Hand(this)
  var currentElixir: Double = 20.0

  val tower: Tower = new Tower(this)
  tower.spawn(if(playersArray.isEmpty) P1_TOWER_POSITION else P2_TOWER_POSITION)

  def deployEntity(entity: Entity with Deployable, position: Vector2): Unit = {
    if (entity.cost <= currentElixir) {
      this.currentElixir -= entity.cost
      entity.spawn(position)
      val entityHandIndex: Int = this.hand.removeEntity(entity)
      this.hand.addEntity(entityHandIndex)
    }else{
      println("Not enough elixir")
    }
  }
}

object Player {

  val playersArray: ArrayBuffer[Player] = new ArrayBuffer()

  val P1_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 230)
  val P2_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 800)

  def createPlayer(): Player = {
    assert(playersArray.length <= 2)
    val player: Player = new Player
    this.playersArray += player
    player
  }

}
