package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.traits.Deployable
import clash_royal_ISC.projectiles.Cat
import com.badlogic.gdx.math.Vector2

class Princess(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 700

  override val cost: Int = 3

  override def copy(): Princess = new Princess(this.player)

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 0
  override val MAX_HEALTH: Int = 5
  override var health: Int = this.MAX_HEALTH
  override val range: Int = 12 * Grid.TILE_SIZE
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 3

  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/princess.png", this.handSpriteWidth, this.handSpriteHeight)



  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10

  override def attack(entity: Entity): Unit = {
    new Cat(this.attackDamage, this.position, this.target).spawn()
  }
}
