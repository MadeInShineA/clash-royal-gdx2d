package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.traits.OnlyTargetBuilding
import clash_royal_ISC.{Grid, Player}
import clash_royal_ISC.entities.Entity

class Giant(player: Player) extends Minion(player) with OnlyTargetBuilding {

  override val moveSpeed: Float = 300f
  override val HAND_SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/giant.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val cost: Int = 5

  override def copy(): Giant = new Giant(this.player)

  override def setTarget(): Unit = super[OnlyTargetBuilding].setTarget()

  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/minions/giant.png", this.HAND_SPRITE_WIDTH, this.HAND_SPRITE_HEIGHT)
  override val SPRITE_WIDTH: Int = 32
  override val SPRITE_HEIGHT: Int = 32
  override var textureY: Int = 1
  override val MAX_HEALTH: Int = 15
  override var health: Int = 15
  override val RANGE: Int = 1 * Grid.TILE_SIZE
  override val ATTACK_SPEED: Int = 1
  override val ATTACK_DAMAGE: Int = 3
  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  val attackSound: SoundSample = new SoundSample("res/sounds/minions/giant/attack.ogg")

  override def attack(entity: Entity) = {
    super.attack(entity)
    attackSound.play()
  }
}
