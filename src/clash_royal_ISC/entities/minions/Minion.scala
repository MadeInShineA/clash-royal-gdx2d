package clash_royal_ISC.entities.minions

import clash_royal_ISC.Player
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.traits.{Deployable, Movable}
import com.badlogic.gdx.math.Vector2

abstract class Minion(player: Player) extends Entity(player) with Deployable with Movable {

  override def spawn(position: Vector2): Unit = {
    super.spawn(position)
    super.setPositions(position)
  }

  override def canMove(): Boolean = {
    !this.targetIsInRange()
  }

  override def setPath(): Unit = {

    val foundPath: List[(Int, Int)] = AStar.findPathWithoutColision(this, (this.position.x.toInt, this.position.y.toInt), (this.target.position.x.toInt, this.target.position.y.toInt))
    this.path = foundPath
  }

  override def update(deltaTime: Float): Unit = {
    super[Entity].update()
    super[Movable].update(deltaTime)

  }

}