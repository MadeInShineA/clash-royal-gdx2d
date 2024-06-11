package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.{Deployable, Entity}

class Wizard(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 400f
  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/Wizard.png", this.handSpriteWidth, this.handSpriteHeight)
  override val cost: Int = 5

  override def copy(): Entity with Deployable = new Wizard(this.player)

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/Wizard.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 5
  override var health: Int = 7
  override val range: Int = 6 * Grid.TILE_SIZE
  override val attackSpeed: Int = 2
  override val attackDamage: Int = 5
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10
}
