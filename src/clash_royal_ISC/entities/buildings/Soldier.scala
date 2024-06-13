package clash_royal_ISC.entities.buildings

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.traits.Deployable
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Bullet
import clash_royal_ISC.{Grid, Player}

class Soldier(player: Player) extends Building(player) with Deployable {

  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/buildings/soldier.png", this.SPRITE_WIDTH, this.SPRITE_HEIGHT)
  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/buildings/soldier.png", this.SPRITE_WIDTH, this.SPRITE_HEIGHT)

  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override val MAX_HEALTH: Int = 15
  override val RANGE: Int = 5 * Grid.TILE_SIZE
  override val ATTACK_SPEED: Int = 2
  override val ATTACK_DAMAGE: Int = 5
  override val COST: Int = 4

  override def attack(entity: Entity): Unit = {
    new Bullet(this.ATTACK_DAMAGE, this.position, this.target).spawn()
  }

  override def copy(): Entity with Deployable = new Soldier(this.player)
}
