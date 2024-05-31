package clash_royal_ISC.entities.buildings
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import com.badlogic.gdx.math.Vector2


class Tower(player: Player, position: Vector2) extends Building(player, position) {

  override val cost: Int = ???
  override var health: Int = ???
  override val range: Int = ???
  override val spriteSheet: Spritesheet = ???
  override val spriteWidth: Int = ???
  override val spriteHeight: Int = ???
  override var textureY: Int = ???
}
