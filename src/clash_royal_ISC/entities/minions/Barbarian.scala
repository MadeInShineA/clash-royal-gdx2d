package clash_royal_ISC.entities.minions
import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.Entity.entitiesArray
import clash_royal_ISC.{Grid, Player}
import com.badlogic.gdx.math.Vector2

class Barbarian(player: Player) extends Minion(player) {

  override val moveSpeed: Float = 400f

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val handSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/barbarian.png", this.handSpriteWidth, this.handSpriteHeight)
  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/barbarian.png", this.spriteWidth, this.spriteHeight)

  override val MAX_HEALTH: Int = 4

  override var health: Int = this.MAX_HEALTH
  override val cost: Int = 1
  override val range: Int = 1 * Grid.TILE_SIZE
  override val attackSpeed: Int = 1
  override val attackDamage: Int = 1

  override var textureY: Int = 1
  override val animationFramesAmount: Int = 4
  override val animationFramesWaitAmount: Int = 10

  val attackSound: SoundSample = new SoundSample("res/sounds/minions/barbarian/attack.ogg")

  override def attack(entity: Entity) = {
    super.attack(entity)
    attackSound.play()
  }




  def copy(): Barbarian = new Barbarian(this.player)

}
