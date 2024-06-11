package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.{Deployable, Entity, OnlyTargetBuilding}

class Giant(player: Player) extends Minion(player) with OnlyTargetBuilding {

  override val moveSpeed: Float = 300f
  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/SantaGiant.png", this.handSpriteWidth, this.handSpriteHeight)
  override val cost: Int = 5

  override def copy(): Entity with Deployable = new Giant(this.player)

  override def setTarget(): Unit = super[OnlyTargetBuilding].setTarget()

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/SantaGiant.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 15
  override var health: Int = 15
  override val range: Int = 1 * Grid.TILE_SIZE
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 3
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10



}
