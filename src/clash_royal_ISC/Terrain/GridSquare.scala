package clash_royal_ISC.Terrain

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.utils.Drawable
import com.badlogic.gdx.graphics.Color


abstract class GridSquare(x: Int, y: Int) extends Drawable{
  val color: Color
  val SIZE = 20

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.drawFilledRectangle(this.x * SIZE, this.y * SIZE, SIZE, SIZE, 0, this.color)
  }

}
