package clash_royal_ISC

import ch.hevs.gdx2d.components.audio.{MusicPlayer, SoundSample}
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

  val GRID: Grid = new Grid
  var player1: Player = _
  var player2: Player = _

  var graphicRenderCounter: Int = 0

  var gameMusicPlayer: MusicPlayer = _

  def resetGame(): Unit = {

    this.gameMusicPlayer.stop()

    selectedEntity = None

    Entity.ENTITIES_ARRAY.clear()
    Projectile.projectilesArray.clear()
    Hand.ENTITIES_ARRAY.clear()
    Player.PLAYERS_ARRAY.clear()

    this.player1 = Player.createPlayer()
    this.player1.setDeployableArray(GRID.tiledMap.getLayers.get(2).asInstanceOf[TiledMapTileLayer])

    this.player2 = Player.createPlayer()
    this.player2.setDeployableArray(GRID.tiledMap.getLayers.get(3).asInstanceOf[TiledMapTileLayer])
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
    }else if(GameWindow.selectedEntity.isDefined &&  GameWindow.selectedEntity.get.PLAYER.DEPLOYABLE_ARRAY(clickXGridCell)(clickYGridCell) ){
      val entity: Entity with Deployable = GameWindow.selectedEntity.get
      entity.PLAYER.deployEntity(entity,new Vector2(x, y))
      GameWindow.selectedEntity = None
    }
  }

  override def onInit(): Unit = {

    new SoundSample("res/sounds/start.mp3").play()
    this.gameMusicPlayer = new MusicPlayer("res/sounds/game.mp3")

    GRID.tiledMap = new TmxMapLoader().load("res/map/map2.tmx")
    Grid.setWalkableArray(GRID.tiledMap)
    GRID.tiledMapRenderer = new OrthogonalTiledMapRenderer(GRID.tiledMap)
    GRID.tiledLayer = GRID.tiledMap.getLayers

    this.player1 = Player.createPlayer()
    this.player1.setDeployableArray(GRID.tiledMap.getLayers.get(2).asInstanceOf[TiledMapTileLayer])

    this.player2 = Player.createPlayer()
    this.player2.setDeployableArray(GRID.tiledMap.getLayers.get(3).asInstanceOf[TiledMapTileLayer])
  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    gdxGraphics.clear()

    if(gameIsRunning){
      this.gameMusicPlayer.setVolume(0.5f)
      this.gameMusicPlayer.loop()
      this.GRID.render(gdxGraphics)

      for(player: Player <- Player.PLAYERS_ARRAY) {
        player.HAND.draw(gdxGraphics)
        if(this.graphicRenderCounter % ELIXIRE_CYCLE_FRAMES == 0 && this.graphicRenderCounter != 0) {
          player.addElixir(0.05f)
        }
        player.drawElixir(gdxGraphics)
      }

      Entity.updateEntities(gdxGraphics, Gdx.graphics.getDeltaTime)
      Projectile.updateProjectiles(gdxGraphics, Gdx.graphics.getDeltaTime )
      gdxGraphics.drawFPS()
      this.graphicRenderCounter += 1
    } else{
      gdxGraphics.drawPicture(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, new BitmapImage("res/menu.jpg"))
      gdxGraphics.drawAlphaPicture(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 1, new BitmapImage("res/menu-text.png"))
      this.graphicRenderCounter += 1
    }
  }
}

object GameWindow {

  val WINDOW_WIDTH: Int = 576
  val WINDOW_HEIGHT: Int = 1024

  val ELIXIRE_CYCLE_FRAMES: Int = 3

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
    new SoundSample("res/sounds/victory.mp3").play()

    this.gameWindowInstance.resetGame()
  }
}

