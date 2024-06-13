package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class Grid {

  var tiledMap: TiledMap = _
  var tiledMapRenderer: OrthogonalTiledMapRenderer = _
  var tiledLayer: MapLayers = _

  def render(gdxGraphics: GdxGraphics): Unit = {
    this.tiledMapRenderer.setView(gdxGraphics.getCamera)
    this.tiledMapRenderer.render(Array(0, 1))
    if(GameWindow.selectedEntity.isDefined){
      val player: Player = GameWindow.selectedEntity.get.PLAYER

      if(player == Player.PLAYERS_ARRAY(0)){
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

  val WALKABLE_ARRAY: Array[Array[Boolean]] = Array.ofDim(GRID_WIDTH, GRID_HEIGHT)

  def setWalkableArray(tiledMap: TiledMap): Unit = {
    val tiledLayer: TiledMapTileLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]

    for (y <- WALKABLE_ARRAY(0).indices) {
      for (x <- WALKABLE_ARRAY.indices) {
        val tile = tiledLayer.getCell(x, y).getTile
        this.WALKABLE_ARRAY(x)(y) = tile.getProperties.get("walkable").toString.toBoolean
      }
    }
  }

  def isPixelValidPathWithoutColision(x: Float, y: Float ,entity: Entity): Boolean = {

    val xInGridCell: Int = x.toInt / this.TILE_SIZE
    val yInGridCell: Int = y.toInt / this.TILE_SIZE

    if(xInGridCell >= this.WALKABLE_ARRAY.length || yInGridCell >= this.WALKABLE_ARRAY(0).length || xInGridCell < 0 || yInGridCell < 0){
      return false
    }

    for(gridEntity: Entity <- Entity.ENTITIES_ARRAY){
      if(gridEntity == entity || gridEntity == entity.target){

      }else{
        val gridEntityXInGridCell = gridEntity.position.x.toInt / this.TILE_SIZE
        val gridEntityYInGridCell = gridEntity.position.y.toInt / this.TILE_SIZE

        if(xInGridCell == gridEntityXInGridCell && yInGridCell == gridEntityYInGridCell){
          return false
        }
      }
    }

    this.WALKABLE_ARRAY(xInGridCell)(yInGridCell)
  }

}
