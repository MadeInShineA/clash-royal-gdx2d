package clash_royal_ISC.entities.minions
import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.{Grid, Player}

class Barbarian(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 400f

  override val SPRITE_WIDTH: Int = 32
  override val SPRITE_HEIGHT: Int = 32

  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/barbarian.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/barbarian.png", this.SPRITE_WIDTH, this.SPRITE_HEIGHT)

  override val MAX_HEALTH: Int = 4

  override var health: Int = this.MAX_HEALTH
  override val cost: Int = 1
  override val RANGE: Int = 1 * Grid.TILE_SIZE
  override val ATTACK_SPEED: Int = 1
  override val ATTACK_DAMAGE: Int = 1

  override var textureY: Int = 1
  override val ANIMATION_FRAME_AMOUNT: Int = 4
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  val attackSound: SoundSample = new SoundSample("res/sounds/minions/barbarian/attack.ogg")

  override def attack(entity: Entity) = {
    super.attack(entity)
    attackSound.play()
  }




  def copy(): Barbarian = new Barbarian(this.player)

}
