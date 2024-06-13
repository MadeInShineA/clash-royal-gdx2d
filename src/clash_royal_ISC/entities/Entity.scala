package clash_royal_ISC.entities

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity.ENTITIES_ARRAY
import clash_royal_ISC.entities.minions.Minion
import com.badlogic.gdx.math.Vector2
import clash_royal_ISC.traits.Drawable

import scala.collection.mutable.ArrayBuffer

abstract class Entity(val PLAYER: Player) extends Drawable {

  val MAX_HEALTH: Int
  val RANGE: Int
  val ATTACK_SPEED: Int
  val ATTACK_DAMAGE: Int

  val HEALTH_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/health-bar.png", 80, 11)
  val HEALTH_SPRITE_WIDTH: Int = 80

  var health: Int = 0

  var position: Vector2 = _

  var target: Entity = _
  var targetInRangeCounter: Int = 0

  def spawn(position: Vector2): Unit = {
    this.health = this.MAX_HEALTH
    this.position = position
    ENTITIES_ARRAY += this
  }

  def dies(): Unit = {
    if(!ENTITIES_ARRAY.contains(this)){
      return
    }
    new SoundSample("res/sounds/death.mp3").play()
    ENTITIES_ARRAY -= this

    for(entity: Entity <- ENTITIES_ARRAY){
      if(entity.target == this){
        entity.target = null
        entity.setTarget()
      }
    }
  }

  def takeDamage(damageAmount: Int): Unit = {
    this.health -= damageAmount
    if (this.health <= 0) {
      this.dies()
    }
  }

  def setTarget(): Unit = {

    if(target != null && this.targetIsInRange()){
      return
    }

    val ennemiEntities = ENTITIES_ARRAY.filter(_.PLAYER != this.PLAYER)

    assert(ennemiEntities.nonEmpty)

    this.target = ennemiEntities.minBy(_.position.dst(this.position))
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    super[Drawable].draw(gdxGraphics)

    val healthPercentage: Float = this.health * 100f / this.MAX_HEALTH

    val healthSprite: Int = healthPercentage match {
      case hp if hp <= 25 => 0
      case hp if hp <= 50 => 1
      case hp if hp <= 75 => 2
      case _ => 3
    }

    gdxGraphics.drawString(this.position.x, this.position.y + 80, this.health.toString)

    gdxGraphics.draw(this.HEALTH_SPRITE_SHEET.sprites(healthSprite)(0), this.position.x - HEALTH_SPRITE_WIDTH / 2, this.position.y + 50)
  }

  def targetIsInRange(): Boolean = {
    this.position.dst(target.position) <= RANGE
  }


  def attack(entity: Entity): Unit = {
    entity.takeDamage(this.ATTACK_DAMAGE)
  }

  def update(): Unit = {
    this.setTarget()

    if(targetIsInRange()){
      this.targetInRangeCounter += 1
    }else{
      this.targetInRangeCounter = 0
    }

    if(this.targetIsInRange() && this.targetInRangeCounter % (100 / this.ATTACK_SPEED) == 0){
      this.attack(this.target)
    }
    this.setDirection(this.target)
  }
}

object Entity {

  val ENTITIES_ARRAY: ArrayBuffer[Entity] = new ArrayBuffer()

  def updateEntities(gdxGraphics: GdxGraphics, deltaTime: Float): Unit = {
    var entityCounter: Int = 0
    while (entityCounter < ENTITIES_ARRAY.length){
      val entity: Entity = ENTITIES_ARRAY(entityCounter)
      entity match {
        case minion: Minion =>
          minion.update(deltaTime)
        case entity =>
          entity.update ()
      }

      entity.draw(gdxGraphics)
      entityCounter += 1
      }
  }
}
