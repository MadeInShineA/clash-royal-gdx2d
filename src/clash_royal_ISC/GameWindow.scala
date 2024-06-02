package clash_royal_ISC

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.GameWindow.{WINDOW_HEIGHT, WINDOW_WIDTH}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

class GameWindow extends PortableApplication(WINDOW_WIDTH, WINDOW_HEIGHT) {

  val grid: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  override def onInit(): Unit = {
    grid.tiledMap = new TmxMapLoader().load("res/map/map.tmx")
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer =  grid.tiledMap.getLayers

    this.player1 = new Player()
    this.player2 = new Player()

    new TestMinion(this.player1, new Vector2(10, 10))

    Gdx.input.setInputProcessor(new MouseListener)

  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.clear()
    gdxGraphics.drawFPS()
//    gdxGraphics.moveCamera(0, -92)
    this.grid.render(gdxGraphics)

    Entity.setTargets()
    Entity.drawEntities(gdxGraphics)

  }
}

object GameWindow {
  val WINDOW_WIDTH: Int = 576
  val WINDOW_HEIGHT: Int = 1080
}

