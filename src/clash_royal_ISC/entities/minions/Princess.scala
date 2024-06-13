package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Cat

class Princess(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 700

  override val cost: Int = 3

  override def copy(): Princess = new Princess(this.player)

  override val SPRITE_WIDTH: Int = 32
  override val SPRITE_HEIGHT: Int = 32
  override var textureY: Int = 0
  override val MAX_HEALTH: Int = 5
  override var health: Int = this.MAX_HEALTH
  override val RANGE: Int = 12 * Grid.TILE_SIZE
  override val ATTACK_SPEED: Int = 1
  override val ATTACK_DAMAGE: Int = 3

  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)



  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override def attack(entity: Entity): Unit = {
    new Cat(this.ATTACK_DAMAGE, this.position, this.target).spawn()
  }
}
