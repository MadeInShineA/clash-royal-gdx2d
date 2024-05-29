package clash_royal_ISC.entities

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity.entitiesArray
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

abstract class Entity(val player: Player, var position: Vector2) extends DrawableObject {

  val cost: Int
  var health: Int
  val range: Int

  val spriteSheet: Spritesheet
  val spriteWidth: Int
  val spriteHeight: Int

  var target: Entity

  var currentAnimationFrame: Int = 0

  var textureY: Int

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

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.draw(this.spriteSheet.sprites(this.textureY)(this.currentAnimationFrame), this.position.x, this.position.y)
  }
}

object Entity {
  val entitiesArray: ArrayBuffer[Entity] = new ArrayBuffer()

  def getTarget(): Unit = {
    for (entity <- entitiesArray) {
      entity.target = findClosestEnemy(entity)
    }
  }

  private def findClosestEnemy(entity: Entity): Option[Entity] = {
    val enemies = entitiesArray.filter(e => e.playerID != entity.playerID)
    if (enemies.isEmpty) {
      None
    } else {
      Some(enemies.minBy(e => entity.position.dst(e.position)))
    }
  }

  def draw(gdxGraphics: GdxGraphics): Unit = {
    entitiesArray.foreach(_.draw(gdxGraphics))
  }


}
