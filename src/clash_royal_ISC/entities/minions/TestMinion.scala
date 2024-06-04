package clash_royal_ISC.entities.minions
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import com.badlogic.gdx.math.Vector2

class TestMinion(player: Player) extends Minion(player) {

  override val moveSpeed: Int = 30

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val handSpriteSheet: Spritesheet = new Spritesheet("res/minions/lumberjack_sheet32.png", this.spriteWidth, this.spriteHeight);

  override val spriteSheet: Spritesheet = new Spritesheet("res/minions/lumberjack_sheet32.png", this.spriteWidth, this.spriteHeight);

  override val cost: Int = 3
  override var health: Int = 10
  override val range: Int = 1
  override var textureY: Int = 1

}
