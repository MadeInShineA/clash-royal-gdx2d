package clash_royal_ISC

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Terrain.Grid

class GameWindow extends PortableApplication(1920, 1080) {

  val grid: Grid = new Grid

  override def onInit(): Unit = {

  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.clear()
    gdxGraphics.drawFPS()
    this.grid.draw(gdxGraphics)
  }

}

