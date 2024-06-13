package clash_royal_ISC.entities.traits

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.entities.Entity

trait Deployable {

  val HAND_SPRITE_SHEET: Spritesheet
  val HAND_SPRITE_WIDTH: Int = 32
  val HAND_SPRITE_HEIGHT: Int = 32
  val COST: Int

  def drawHandSprite(xPosition: Float,yPosition: Float, gdxGraphics: GdxGraphics) = {
    gdxGraphics.draw(HAND_SPRITE_SHEET.sprites(0)(0), xPosition, yPosition)
  }

  def copy(): Entity with Deployable
}
