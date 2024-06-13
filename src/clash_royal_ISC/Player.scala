package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Player.{P1_ELIXIR_POSITION, P1_TOWER_POSITION, P2_ELIXIR_POSITION, P2_TOWER_POSITION, playersArray}
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.buildings.Commander
import clash_royal_ISC.entities.traits.Deployable
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer


class Player private {

  val MAX_ELIXIR: Float = 18
  val ELIXIR_POSITION: Vector2 = if(playersArray.isEmpty) P1_ELIXIR_POSITION else P2_ELIXIR_POSITION

  var deployableArray: Array[Array[Boolean]] = Array.ofDim(Grid.GRID_WIDTH, Grid.GRID_HEIGHT)
  var hand: Hand = new Hand(this)
  var currentElixir: Float = 3

  var tower: Commander = new Commander(this)
  tower.spawn(if(playersArray.isEmpty) P1_TOWER_POSITION else P2_TOWER_POSITION)

  def setDeployableArray(tiledMapLayer: TiledMapTileLayer): Unit = {
    for (y <- deployableArray(0).indices) {
      for (x <- deployableArray.indices) {
        val tile = tiledMapLayer.getCell(x, y).getTile
        this.deployableArray(x)(y) = tile.getProperties.get("deployable").toString.toBoolean
      }
    }
  }

  def deployEntity(entity: Entity with Deployable, position: Vector2): Unit = {
    if (entity.cost <= currentElixir) {
      this.currentElixir -= entity.cost
      entity.spawn(position)
      val entityHandIndex: Int = this.hand.removeEntity(entity)
      this.hand.addEntity(entityHandIndex)
    }
  }

  def addElixir(elixirAmount: Float): Unit = {
    if(this.currentElixir <= this.MAX_ELIXIR){
      if(this.currentElixir + elixirAmount <= this.MAX_ELIXIR){
        this.currentElixir += elixirAmount
        if(this.currentElixir % 2 == 0){
//          new SoundSample("res/sounds/elixir.mp3").play()
        }
      }else{
        this.currentElixir = MAX_ELIXIR
      }
    }
  }

  def drawElixir(gdxGraphics: GdxGraphics): Unit = {
    gdxGraphics.setColor(Color.PINK)
    val elixirBarWidth = Grid.TILE_SIZE * this.currentElixir
    val elixirBarHeight = 1 * Grid.TILE_SIZE
    val elixirBarX = this.ELIXIR_POSITION.x - (MAX_ELIXIR * Grid.TILE_SIZE / 2) + (elixirBarWidth / 2)
    gdxGraphics.drawFilledRectangle(elixirBarX, this.ELIXIR_POSITION.y, elixirBarWidth, elixirBarHeight, 0)
  }
}

object Player {

  val playersArray: ArrayBuffer[Player] = new ArrayBuffer()

  val P1_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 180)
  val P2_TOWER_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 830)

  val P1_ELIXIR_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, Grid.TILE_SIZE / 2)
  val P2_ELIXIR_POSITION: Vector2 = new Vector2(GameWindow.WINDOW_WIDTH / 2, 31 * Grid.TILE_SIZE + Grid.TILE_SIZE / 2)

  def createPlayer(): Player = {
    assert(playersArray.length <= 2)
    val player: Player = new Player
    this.playersArray += player
    player
  }
}
