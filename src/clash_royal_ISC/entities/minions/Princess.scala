package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.{Deployable, Entity}

class Princess(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 200f
  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/Princess.png", this.handSpriteWidth, this.handSpriteHeight)
  override val cost: Int = 3

  override def copy(): Entity with Deployable = new Princess(this.player)

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/Princess.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 4
  override var health: Int = 4
  override val range: Int = 12 * Grid.TILE_SIZE
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 3
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10
}
