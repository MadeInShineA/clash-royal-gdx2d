package clash_royal_ISC.Terrain

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Terrain.Tiles.GroundTile
import clash_royal_ISC.utils.Drawable

class Grid extends Drawable{

  val HEIGHT: Int = 32
  val WIDTH: Int = 18

  val squares: Array[Array[GridSquare]] = createGrid

  def createGrid: Array[Array[GridSquare]] = {
    val res: Array[Array[GridSquare]] = Array.ofDim(HEIGHT, WIDTH)
    for(y: Int <- 0 until HEIGHT){
      for(x: Int <- 0 until WIDTH){
        res(y)(x) = new GroundTile(x, y)
      }
    }
    res
  }

  def draw(gdxGraphics: GdxGraphics): Unit = {
    squares.map(_.map(_.draw(gdxGraphics)))
  }
}
