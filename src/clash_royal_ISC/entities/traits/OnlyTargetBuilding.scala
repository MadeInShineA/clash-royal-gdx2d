package clash_royal_ISC.entities.traits

import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.Entity.entitiesArray
import clash_royal_ISC.entities.buildings.Building
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

trait OnlyTargetBuilding {
  var target: Entity
  val player: Player
  var position: Vector2
  def targetIsInRange(): Boolean
  def setTarget(): Unit = {
    if(target != null && this.targetIsInRange()){
      return
    }

    val ennemiEntities: ArrayBuffer[Entity] = new ArrayBuffer
    for (entitie <- entitiesArray) {
      if (entitie.player != this.player && entitie.isInstanceOf[Building]) {
        ennemiEntities.append(entitie)
      }
    }
    assert(ennemiEntities.nonEmpty)
    this.target = ennemiEntities.minBy(_.position.dst(this.position))
  }
}
