package clash_royal_ISC.entities.buildings
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Typhoon
import clash_royal_ISC.{GameWindow, Grid, Player}
import com.badlogic.gdx.math.Vector2


class Commander(player: Player) extends Building(player) {

  override val SPRITE_SHEET: Spritesheet = new Spritesheet("res/sprites/buildings/commander.png", this.SPRITE_WIDTH, this.SPRITE_HEIGHT);

  override val ANIMATION_FRAME_AMOUNT: Int = 3
  override val ANIMATION_FRAMES_WAIT_AMOUNT: Int = 10

  override val MAX_HEALTH: Int = 50
  override val RANGE: Int = 10 * Grid.TILE_SIZE
  override val ATTACK_SPEED: Int = 2
  override val ATTACK_DAMAGE: Int = 1

  override def attack(entity: Entity): Unit = {
    new Typhoon(this.ATTACK_DAMAGE, this.position, this.target).spawn()
  }

  override def dies(): Unit = {
    GameWindow.endGame()
  }
}
