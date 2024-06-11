package clash_royal_ISC

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.GameWindow.{ELIXIRE_CYCLE_FRAMES, WINDOW_HEIGHT, WINDOW_WIDTH, gameIsRunning, selectedEntity}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.traits.Deployable
import clash_royal_ISC.projectiles.Projectile
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Gdx, Input}

class GameWindow extends PortableApplication(WINDOW_WIDTH, WINDOW_HEIGHT) {

  val grid: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  var graphicRenderCounter: Int = 0

  def resetGame(): Unit = {

    selectedEntity = None

    Entity.entitiesArray.clear()
    Projectile.projectilesArray.clear()
    Hand.entitiesArray.clear()

    Player.playersArray.clear()

    this.player1 = Player.createPlayer()
    this.player1.setDeployableArray(grid.tiledMap.getLayers.get(2).asInstanceOf[TiledMapTileLayer])


    this.player2 = Player.createPlayer()
    this.player2.setDeployableArray(grid.tiledMap.getLayers.get(3).asInstanceOf[TiledMapTileLayer])



  }

  override def onKeyDown(keyCode: Int): Unit = {
    super.onKeyDown(keyCode)
    if(keyCode == Input.Keys.ENTER && !gameIsRunning){
      gameIsRunning = true
    }
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)

    val clickXGridCell: Int = x / Grid.TILE_SIZE
    val clickYGridCell: Int = y / Grid.TILE_SIZE

    val selectedEntity: Option[Entity with Deployable] = Hand.getEntityAtPosition(x, y)

    if(selectedEntity.isDefined){
      GameWindow.selectedEntity = Hand.getEntityAtPosition(x, y)
    }else if(GameWindow.selectedEntity.isDefined &&  GameWindow.selectedEntity.get.player.deployableArray(clickXGridCell)(clickYGridCell) ){
      val entity: Entity with Deployable = GameWindow.selectedEntity.get
      entity.player.deployEntity(entity,new Vector2(x, y))
      GameWindow.selectedEntity = None
    }
  }

  override def onInit(): Unit = {

    new SoundSample("res/sounds/start.mp3").play()
    Thread.sleep(3800)

    grid.tiledMap = new TmxMapLoader().load("res/sprites/map/map2.tmx")
    Grid.setWalkableArray(grid.tiledMap)
    grid.tiledMapRenderer = new OrthogonalTiledMapRenderer(grid.tiledMap)
    grid.tiledLayer = grid.tiledMap.getLayers

    this.player1 = Player.createPlayer()
    this.player1.setDeployableArray(grid.tiledMap.getLayers.get(2).asInstanceOf[TiledMapTileLayer])

    this.player2 = Player.createPlayer()
    this.player2.setDeployableArray(grid.tiledMap.getLayers.get(3).asInstanceOf[TiledMapTileLayer])
  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    gdxGraphics.clear()

    if(gameIsRunning){
      this.grid.render(gdxGraphics)

      for(player: Player <- Player.playersArray) {
        player.hand.draw(gdxGraphics)
        if(this.graphicRenderCounter % ELIXIRE_CYCLE_FRAMES == 0 && this.graphicRenderCounter != 0) {
          player.addElixir(0.5f)
        }
        player.drawElixir(gdxGraphics)
      }

      Entity.updateEntities(gdxGraphics, Gdx.graphics.getDeltaTime)
      Projectile.updateProjectiles(gdxGraphics, Gdx.graphics.getDeltaTime )
      gdxGraphics.drawFPS()
      this.graphicRenderCounter += 1

    }
    else{
      gdxGraphics.drawPicture(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, new BitmapImage("res/menu.png"))
      gdxGraphics.drawAlphaPicture(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 1, new BitmapImage("res/menu-text.png"))
      this.graphicRenderCounter += 1
    }
  }
}

object GameWindow {
  val WINDOW_WIDTH: Int = 576
  val WINDOW_HEIGHT: Int = 1024

  val ELIXIRE_CYCLE_FRAMES: Int = 30

  var selectedEntity: Option[Entity with Deployable] = None

  var gameIsRunning: Boolean = false

  // TODO Is there a better way to do so ?
  var gameWindowInstance: GameWindow = _

  def createGameWindow(): Unit = {
    assert(this.gameWindowInstance == null)
    this.gameWindowInstance = new GameWindow
  }
  def endGame(): Unit = {
    gameIsRunning = false
    println(s"The game has ended")
    new SoundSample("res/sounds/victory.mp3").play()

    this.gameWindowInstance.resetGame()

  }


}

