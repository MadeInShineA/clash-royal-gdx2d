package clash_royal_ISC.entities.buildings
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import com.badlogic.gdx.math.Vector2


class Tower(player: Player, position: Vector2) extends Building(player, position) {

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val spriteSheet: Spritesheet = new Spritesheet("res/minions/lumberjack_sheet32.png", this.spriteWidth, this.spriteHeight);

  override var health: Int = 100
  override val range: Int = 2
  override var textureY: Int = 1

  this.spawn(this.position)
}
