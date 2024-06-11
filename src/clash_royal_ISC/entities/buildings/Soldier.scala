package clash_royal_ISC.entities.buildings

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.traits.Deployable
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.CannonBall
import clash_royal_ISC.{Grid, Player}

class Soldier(player: Player) extends Building(player) with Deployable {

  override val cost: Int = 4
  override def copy(): Entity with Deployable = new Soldier(this.player)

  override val spriteWidth: Int = 32

  override val spriteHeight: Int = 32
  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/buildings/soldier.png", this.spriteWidth, this.spriteHeight)

  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/buildings/soldier.png", this.spriteWidth, this.spriteHeight)
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 15
  override var health: Int = this.MAX_HEALTH
  override val range: Int = 5 * Grid.TILE_SIZE
  override val attackSpeed: Int = 2
  override val attackDamage: Int = 5
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10

  override def attack(entity: Entity): Unit = {
    new CannonBall(this.attackDamage, this.position, this.target)
  }

}
