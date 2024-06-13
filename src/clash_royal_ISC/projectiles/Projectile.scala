package clash_royal_ISC.projectiles

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Grid
import clash_royal_ISC.utils.AStar
import clash_royal_ISC.entities.Entity
import Projectile.projectilesArray
import ch.hevs.gdx2d.components.audio.SoundSample
import clash_royal_ISC.traits.Drawable
import clash_royal_ISC.traits.Movable
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

abstract class Projectile(val attackDamage: Int, var position: Vector2, val target: Entity) extends Drawable with Movable {

  val range: Int = 1 * Grid.TILE_SIZE

  val spawnSound: SoundSample
  val hitSound: SoundSample

  def spawn(): Unit = {
    projectilesArray += this
    super.setPositions(position)
    this.spawnSound.play()
  }


  override def canMove(): Boolean = {
    true
  }

  override def setPath(): Unit = {

    val foundPath: List[(Int, Int)] = AStar.findAbsolutePath((this.position.x.toInt, this.position.y.toInt), (this.target.position.x.toInt, this.target.position.y.toInt))
    this.path = foundPath
  }

  def hasReachedTarget(): Boolean = {
    this.position.dst(this.target.position) <= this.range ||  this.path.isEmpty
  }

  override def update(deltaTime: Float): Unit = {
    super.update(deltaTime)
    setDirection(this.target)
  }


}



object Projectile {
  val projectilesArray: ArrayBuffer[Projectile] = new ArrayBuffer()

  def updateProjectiles(gdxGraphics: GdxGraphics, deltaTime: Float): Unit = {
    var projectileCounter: Int = 0
    while(projectileCounter < projectilesArray.length){
      val projectile = projectilesArray(projectileCounter)
      projectile.update(deltaTime)
      projectile.draw(gdxGraphics)
      if(projectile.hasReachedTarget()){
        this.projectilesArray.remove(projectileCounter)
        projectile.target.takeDamage(projectile.attackDamage)
        projectile.hitSound.play()
      }
      projectileCounter += 1
    }
  }
}
