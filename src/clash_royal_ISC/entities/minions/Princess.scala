package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Cat

class Princess(player: Player) extends Minion(player) {

  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)

  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override val MAX_HEALTH: Int = 5
  override val RANGE: Int = 12 * Grid.TILE_SIZE
  override val MOVE_SPEED: Float = 700
  override val ATTACK_SPEED: Int = 1
  override val ATTACK_DAMAGE: Int = 3
  override val COST: Int = 3

  override def attack(entity: Entity): Unit = {
    new Cat(this.ATTACK_DAMAGE, this.position, this.target).spawn()
  }

  override def copy(): Princess = new Princess(this.player)
}
