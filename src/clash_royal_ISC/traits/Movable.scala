package clash_royal_ISC.traits

import com.badlogic.gdx.math.Vector2

trait Movable extends {

  val moveSpeed: Float
  var path: List[(Int, Int)] = _

  var dt: Float = 0
  var position: Vector2
  var lastPosition: Vector2 = _
  var newPosition: Vector2 = _

  def canMove(): Boolean

  def setPath(): Unit

  def setPositions(position: Vector2): Unit = {
    this.lastPosition = position
    this.newPosition = position
  }





  def move(deltaTime: Float): Unit = {
    if(path.isEmpty){
      this.setPath()
      return
    }

    if (!this.canMove()) return

    val frameTime: Float = 0.01f
    this.dt += deltaTime

    while (this.dt > frameTime && path.nonEmpty) {
      this.dt -= frameTime

      // Mettre à jour lastPosition à la position courante
      this.lastPosition = new Vector2(this.position)

      // Vérifier si le chemin doit être mis à jour
      if (this.path.nonEmpty) {
        val nextPosition = new Vector2(path.head._1, path.head._2)
        this.newPosition = nextPosition
        if (this.position == new Vector2(this.path.head._1, this.path.head._2)) {
          this.path = this.path.tail
        }
      }
    }

    // Calculer la distance maximale que l'entité peut parcourir pendant cette frame
    val maxDistancePerFrame = this.moveSpeed * this.dt

    // Calculer la distance entre lastPosition et newPosition
    val distance = this.lastPosition.dst(this.newPosition)

    // Si la distance est inférieure ou égale à la distance maximale, l'entité atteint la nouvelle position
    if (distance <= maxDistancePerFrame) {
      this.position.set(this.newPosition)

    } else {
      // Sinon, l'entité se déplace vers la nouvelle position avec interpolation
      val alpha = Math.min(maxDistancePerFrame / distance, 1.0f)
      this.position = this.lastPosition.lerp(this.newPosition, alpha)
    }

    this.setPath()

  }

  def update(deltaTime: Float): Unit = {
    if (this.path == null) {
      this.setPath()
    }

    this.move(deltaTime)
  }

}
