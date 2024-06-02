package clash_royal_ISC.entities

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity.{entitiesArray, findClosestEnemy}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

abstract class Entity(val player: Player, var position: Vector2) extends DrawableObject {

  val spriteSheet: Spritesheet
  val spriteWidth: Int
  val spriteHeight: Int
  var textureY: Int

  var health: Int
  val range: Int

  var target: Entity = _

  var currentAnimationFrame: Int = 0


  object Direction extends Enumeration {
    val UP, DOWN, LEFT, RIGHT = Value
  }

  def spawn(position: Vector2): Unit = {
    this.position = position
    entitiesArray += this
  }

  def dies(): Unit = {
    entitiesArray -= this
  }

  def takeDamage(damageAmount: Int): Unit = {
    this.health -= damageAmount
    if (this.health <= 0) {
      this.dies()
    }
  }

  def turn(direction: Direction.Value): Unit = {
    direction match {
      case Direction.UP => {
        this.textureY = 3
      }
      case Direction.DOWN => {
        this.textureY = 0
      }
      case Direction.LEFT => {
        this.textureY = 1
      }
      case Direction.RIGHT => {
        this.textureY = 2
      }
      case _ => {}
    }
  }

  //  override def draw(gdxGraphics: GdxGraphics): Unit = {
  //    gdxGraphics.draw(this.spriteSheet.sprites(this.textureY)(this.currentAnimationFrame), this.position.x, this.position.y)
  //  }
  override def draw(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.drawCircle(this.position.x, this.position.y, 20f, Color.BLACK)
  }

}

object Entity {
  val entitiesArray: ArrayBuffer[Entity] = new ArrayBuffer()

  def setTargets(): Unit = {
    for (entity <- entitiesArray) {
      entity.target = findClosestEnemy(entity)
    }
  }

  private def findClosestEnemy(entity: Entity): Entity = {

    val ennemiEntities = entitiesArray.filter(_.player != entity.player)

    // The array shouldn't ever be empty when the function is called since each player has at least 1 Tower
    assert(ennemiEntities.nonEmpty)

    ennemiEntities.minBy(_.position.dst(entity.position))

  }

  def drawEntities(gdxGraphics: GdxGraphics): Unit = {
    entitiesArray.foreach(_.draw(gdxGraphics))
  }
}
