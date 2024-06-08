package clash_royal_ISC

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.GameWindow.{ELIXIRE_CYCLE_FRAMES, WINDOW_HEIGHT, WINDOW_WIDTH, endGame, gameIsRunning, selectedEntity}
import clash_royal_ISC.Utils.AStar
import clash_royal_ISC.entities.buildings.Tower
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

import java.lang
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

class GameWindow extends PortableApplication(WINDOW_WIDTH, WINDOW_HEIGHT) {

  val grid: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  var graphicRenderCounter: Int = 0

  def resetGame(): Unit = {
//    new SoundSample("res/sounds/start.mp3").play()
//
//    Thread.sleep(3800)


    selectedEntity = None

    Entity.entitiesArray.clear()
    Hand.entitiesArray.clear()

    Player.playersArray.clear()

    this.player1 = Player.createPlayer()
    this.player2 = Player.createPlayer()


  }

  override def onInit(): Unit = {
    grid.tiledMap = new TmxMapLoader().load("res/sprites/map/map2.tmx")
    Grid.setWalkableArray(grid.tiledMap)
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer = grid.tiledMap.getLayers

    Gdx.input.setInputProcessor(new MouseListener)

    this.player1 = Player.createPlayer()
    this.player2 = Player.createPlayer()
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

  var gameIsRunning: Boolean = false

  var gameWindowInstance: GameWindow = _

  def createGameWindow(): Unit = {
    this.gameWindowInstance = new GameWindow
  }
  def endGame(): Unit = {
    gameIsRunning = false
    println(s"The game has ended")

    new SoundSample("res/sounds/victory.mp3").play()
    lang.Thread.sleep(1500)

    this.gameWindowInstance.resetGame()

  }


}

