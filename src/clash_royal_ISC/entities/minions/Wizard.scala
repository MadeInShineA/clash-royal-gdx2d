package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import clash_royal_ISC.entities.{Deployable, Entity}

class Wizard(player: Player) extends Minion(player) {

  override val moveSpeed: Float = ???
  override val handSpriteSheet: Spritesheet = ???
  override val cost: Int = ???

  override def copy(): Entity with Deployable = ???

  override val spriteSheet: Spritesheet = ???
  override val spriteWidth: Int = ???
  override val spriteHeight: Int = ???
  override var textureY: Int = ???
  override val MAX_HEALTH: Int = ???
  override var health: Int = ???
  override val range: Int = ???
  override val attackSpeed: Int = ???
  override val attackDamage: Int = ???
  override val animationFramesAmount: Int = ???
  override val animationFramesWaitAmount: Int = ???
}
