package clash_royal_ISC

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.GameWindow.{WINDOW_HEIGHT, WINDOW_WIDTH}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

import scala.concurrent.Future

class GameWindow extends PortableApplication(WINDOW_WIDTH, WINDOW_HEIGHT) {

  val grid: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  var graphicRenderCounter: Int = 0

  override def onInit(): Unit = {
    grid.tiledMap = new TmxMapLoader().load("res/map/map2.tmx")
    AStar.walkableArray = AStar.convertMapToBoolArray(grid.tiledMap)
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer = grid.tiledMap.getLayers

    this.player1 = new Player()
    this.player2 = new Player()

    val testMinion: TestMinion = new TestMinion(this.player1)
    val testMinion2: TestMinion = new TestMinion(this.player2)
    val testMinion3: TestMinion = new TestMinion(this.player2)
//
    testMinion.spawn(new Vector2(30, 130))
//    testMinion2.spawn(new Vector2(180, 800))
//    testMinion3.spawn(new Vector2(500, 800))
//    Gdx.input.setInputProcessor(new MouseListener)



  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.clear()


    this.grid.render(gdxGraphics)

    Entity.updateEntities(gdxGraphics, Gdx.graphics.getDeltaTime)

//    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
//
//    if(this.graphicRenderCounter % 5 == 0) {
//      println("Setting path Async")
//      Future(Entity.setEntitiesPathAsync())
//    }
    gdxGraphics.drawFPS()
    this.graphicRenderCounter += 1
  }
}

object GameWindow {
  val WINDOW_WIDTH: Int = 576
  val WINDOW_HEIGHT: Int = 1024

//  val FRAME_TIME: Float = 1 / 60f
  val FRAME_TIME: Float = 0.1f


}

