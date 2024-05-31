package clash_royal_ISC.entities.minions

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

abstract class Minion(player: Player, position: Vector2) extends Entity(player, position) {
  val moveSpeed: Int
  val handSpriteSheet: Spritesheet

  def move(target: Entity): Unit = {
    val deltaTime = Gdx.graphics.getDeltaTime

    // Calculer la direction vers le target
    val directionX = target.position.x - this.position.x
    val directionY = target.position.y - this.position.y
    val distance = math.sqrt(directionX * directionX + directionY * directionY).toFloat

    // Normaliser le vecteur de direction
    val normalizedX = directionX / distance
    val normalizedY = directionY / distance

    // Mettre à jour la position en fonction de la moveSpeed
    this.position.x += normalizedX * moveSpeed * deltaTime
    this.position.y += normalizedY * moveSpeed * deltaTime
  }
}
