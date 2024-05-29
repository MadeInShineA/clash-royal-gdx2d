package clash_royal_ISC

import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer

class Player(val playerID: Int) {
  var hand: ArrayBuffer[Entity] = _
  var currentElixir: Double = 0.0
  def deployEntity(entity: Entity, position: Vector2): Unit = {
    if (entity.cost <= currentElixir) {
      entity.spawn(position)
      this.currentElixir -= entity.cost
      this.addEntity()
    }
  }

  def addEntity(): Unit = {
    //fonction qui remet une carte dans la main
  }
}
