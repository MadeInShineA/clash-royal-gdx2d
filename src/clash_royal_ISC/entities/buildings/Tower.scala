package clash_royal_ISC.entities.buildings
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.{Grid, Player}
import com.badlogic.gdx.math.Vector2


class Tower(player: Player) extends Building(player) {

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/lumberjack_sheet32.png", this.spriteWidth, this.spriteHeight);

  override var health: Float = 10
  override val range: Int = 4 * Grid.tileSize
  override var textureY: Int = 1
  override val animationFramesAmount: Int = 4
  override val animationFramesWaitAmount: Int = 10

  override val MAX_HEALTH: Int = 10

  override val attackSpeed: Int = 3
  override val attackDamage: Float = 2
}
