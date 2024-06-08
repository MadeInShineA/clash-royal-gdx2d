package clash_royal_ISC.entities.minions
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}

class TestMinion(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 450f

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/lumberjack_sheet32.png", this.handSpriteWidth, this.handSpriteHeight);
  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/lumberjack_sheet32.png", this.spriteWidth, this.spriteHeight);

  override val MAX_HEALTH: Int = 4

  override var health: Int = this.MAX_HEALTH
  override val cost: Int = 4
  override val range: Int = 3 * Grid.tileSize
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 1

  override var textureY: Int = 1
  override val animationFramesAmount: Int = 4
  override val animationFramesWaitAmount: Int = 10


  def copy(): TestMinion = {
    new TestMinion(this.player)
  }

}
