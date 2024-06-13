package clash_royal_ISC.projectiles

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.math.Vector2

class Fireball(attackDamage: Int ,position: Vector2, target: Entity) extends Projectile(attackDamage, position, target) {

  override val moveSpeed: Float = 450
  override val SPRITE_WIDTH: Int = 32
  override val SPRITE_HEIGHT: Int = 32
  override var textureY: Int = 0



  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/projectiles/fireball.png", this.SPRITE_WIDTH, this.SPRITE_HEIGHT)

  override val ANIMATION_FRAME_AMOUNT: Int = 4
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override val spawnSound: SoundSample = new SoundSample("res/sounds/projectiles/fireball/spawn.mp3")
  override val hitSound: SoundSample = new SoundSample("res/sounds/projectiles/fireball/hit.ogg")
}
