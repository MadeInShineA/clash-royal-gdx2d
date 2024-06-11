package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Fireball

class Wizard(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 300f
  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/wizard.png", this.handSpriteWidth, this.handSpriteHeight)
  override val cost: Int = 5

  override def copy(): Wizard = new Wizard(this.player)

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/wizard.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 15
  override var health: Int = 15
  override val range: Int = 10 * Grid.TILE_SIZE
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 3
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10

  override def attack(entity: Entity): Unit = {
    new Fireball(this.attackDamage, this.position, entity)
  }
}
