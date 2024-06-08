package clash_royal_ISC

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.GameWindow.{ELIXIRE_CYCLE_FRAMES, WINDOW_HEIGHT, WINDOW_WIDTH, endGame, gameIsRunning}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.buildings.Tower
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

import java.lang
import scala.concurrent.Future

class GameWindow extends PortableApplication(WINDOW_WIDTH, WINDOW_HEIGHT) {

  val grid: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  var graphicRenderCounter: Int = 0

  override def onInit(): Unit = {
    new SoundSample("res/sounds/start.mp3").play()

    Thread.sleep(3800)
    grid.tiledMap = new TmxMapLoader().load("res/sprites/map/map2.tmx")
    Grid.setWalkableArray(grid.tiledMap)
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer = grid.tiledMap.getLayers

    this.player1 = Player.createPlayer()
    this.player2 = Player.createPlayer()

//    val testMinion: TestMinion = new TestMinion(this.player1)
//    val testMinion2: TestMinion = new TestMinion(this.player2)
//    val testMinion3: TestMinion = new TestMinion(this.player2)
//    val testMinion4: TestMinion = new TestMinion(this.player1)
//    testMinion.spawn(new Vector2(30, 130))
//    testMinion2.spawn(new Vector2(180, 800))
//    testMinion3.spawn(new Vector2(500, 800))
//    testMinion4.spawn(new Vector2(450, 130))
    Gdx.input.setInputProcessor(new MouseListener)

  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.clear()
    this.grid.render(gdxGraphics)

    for(player: Player <- Player.playersArray) {
      player.hand.draw(gdxGraphics)
      if(this.graphicRenderCounter % ELIXIRE_CYCLE_FRAMES == 0 && this.graphicRenderCounter != 0) {
        player.addElixir(0.5f)
      }
      player.drawElixir(gdxGraphics)
      }

    Entity.updateEntities(gdxGraphics, Gdx.graphics.getDeltaTime)
    gdxGraphics.drawFPS()
    this.graphicRenderCounter += 1
  }


}

object GameWindow {
  val WINDOW_WIDTH: Int = 576
  val WINDOW_HEIGHT: Int = 1024

  val ELIXIRE_CYCLE_FRAMES: Int = 30

  var selectedEntity: Option[Entity with Deployable] = None

  var gameIsRunning: Boolean = true

  def endGame(): Unit = {
    gameIsRunning = false
    println(s"The game has ended")

    new SoundSample("res/sounds/victory.mp3").play()
    lang.Thread.sleep(1500)

    System.exit(1)
  }


}

