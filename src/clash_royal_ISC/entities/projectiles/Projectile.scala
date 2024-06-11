package clash_royal_ISC.entities.projectiles

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity

abstract class Projectile(shooter: Entity, target: Entity){

  val moveSpeed: Float
  val spriteSheet: Spritesheet
  val spriteWidth: Int
  val spriteHeight: Int
  var textureY: Int







}
