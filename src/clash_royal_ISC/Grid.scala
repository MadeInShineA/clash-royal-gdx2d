package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.Grid.walkableArray
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTile, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class Grid {

  var tiledMap: TiledMap = _
  var tiledMapRenderer: OrthogonalTiledMapRenderer = _
  var tiledLayer: MapLayers = _


  def render(gdxGraphics: GdxGraphics): Unit = {
    this.tiledMapRenderer.setView(gdxGraphics.getCamera)
    this.tiledMapRenderer.render(Array(0, 1))
    if(GameWindow.selectedEntity.isDefined){
      val player: Player = GameWindow.selectedEntity.get.player

      if(player == Player.playersArray(0)){
        this.tiledMapRenderer.render(Array(2))
      }else{
        this.tiledMapRenderer.render(Array(3))
      }
    }
  }
}

object Grid {
  val TILE_SIZE: Int = 32
  val GRID_WIDTH: Int = 18
  val GRID_HEIGHT: Int = 32

  val walkableArray: Array[Array[Boolean]] = Array.ofDim(GRID_WIDTH, GRID_HEIGHT)

  def setWalkableArray(tiledMap: TiledMap): Unit = {
    val tiledLayer: TiledMapTileLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]

    for (y <- walkableArray(0).indices) {
      for (x <- walkableArray.indices) {
        val tile = tiledLayer.getCell(x, y).getTile
        this.walkableArray(x)(y) = tile.getProperties.get("walkable").toString.toBoolean
      }
    }
  }

  def isPixelValidPathWithoutColision(x: Float, y: Float ,entity: Entity): Boolean = {

    val xInGridCell: Int = x.toInt / this.TILE_SIZE
    val yInGridCell: Int = y.toInt / this.TILE_SIZE

    if(xInGridCell >= this.walkableArray.length || yInGridCell >= this.walkableArray(0).length || xInGridCell < 0 || yInGridCell < 0){
      return false
    }

    for(gridEntity: Entity <- Entity.entitiesArray){
      if(gridEntity == entity || gridEntity == entity.target){

      }else{
        val gridEntityXInGridCell = gridEntity.position.x.toInt / this.TILE_SIZE
        val gridEntityYInGridCell = gridEntity.position.y.toInt / this.TILE_SIZE

        if(xInGridCell == gridEntityXInGridCell && yInGridCell == gridEntityYInGridCell){
          return false
        }
      }
    }

    this.walkableArray(xInGridCell)(yInGridCell)
  }

  def isPixelValidPath(x: Float, y: Float): Boolean = {
    val xInGridCell: Int = x.toInt / this.TILE_SIZE
    val yInGridCell: Int = y.toInt / this.TILE_SIZE

    if(xInGridCell >= this.walkableArray.length || yInGridCell >= this.walkableArray(0).length || xInGridCell < 0 || yInGridCell < 0) {
      return false
    }

    this.walkableArray(xInGridCell)(yInGridCell)
  }
}
