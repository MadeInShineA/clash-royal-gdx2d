package clash_royal_ISC.entities.minions

abstract class Minion extends Entity {
  val moveSpeed: Int
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
