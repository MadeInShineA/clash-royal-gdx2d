package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Fireball

class Wizard(player: Player) extends Minion(player) {

  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/wizard.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/wizard.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)

  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override val MAX_HEALTH: Int = 15
  override val RANGE: Int = 10 * Grid.TILE_SIZE
  override val MOVE_SPEED: Float = 300f
  override val ATTACK_SPEED: Int = 1
  override val ATTACK_DAMAGE: Int = 3
  override val COST: Int = 5

  override def attack(entity: Entity): Unit = {
    new Fireball(this.ATTACK_DAMAGE, this.position, entity).spawn()
  }

  override def copy(): Wizard = new Wizard(this.player)
}
