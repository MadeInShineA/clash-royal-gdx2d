package clash_royal_ISC.entities

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

trait Deployable {

  val handSpriteSheet: Spritesheet
  val handSpriteWidth: Int = 32
  val handSpriteHeight: Int = 32
  val cost: Int

  def drawHandSprite(xPosition: Float,yPosition: Float, gdxGraphics: GdxGraphics) = {
    gdxGraphics.draw(handSpriteSheet.sprites(0)(0), xPosition, yPosition)
  }

  def copy(): Entity with Deployable

}
