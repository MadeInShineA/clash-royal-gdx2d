package clash_royal_ISC

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class GameWindow extends PortableApplication(576, 1080) {

  val grid: Grid = new Grid


  override def onInit(): Unit = {
    grid.tiledMap = new TmxMapLoader().load("res/map.tmx")
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer =  grid.tiledMap.getLayers
  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.clear()
    gdxGraphics.drawFPS()

    gdxGraphics.moveCamera(0, -92)
    grid.tiledMapRenderer.setView(gdxGraphics.getCamera)
    grid.tiledMapRenderer.render()
  }

}

