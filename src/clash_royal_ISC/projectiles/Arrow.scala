package clash_royal_ISC.projectiles
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.math.Vector2

class Arrow(attackDamage: Int ,position: Vector2, target: Entity) extends Projectile(attackDamage, position, target) {

  override val moveSpeed: Float = 600
  override val spriteSheet: Spritesheet = ???
  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32
  override var textureY: Int = 0
  override val animationFramesAmount: Int = 4
  override val animationFramesWaitAmount: Int = 10
}
