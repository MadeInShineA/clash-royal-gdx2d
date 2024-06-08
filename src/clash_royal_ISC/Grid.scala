package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTile, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2

class Grid {

  var tiledMap: TiledMap = _
  var tiledMapRenderer: OrthogonalTiledMapRenderer = _
  var tiledLayer: MapLayers = _


  def render(gdxGraphics: GdxGraphics): Unit = {
    this.tiledMapRenderer.setView(gdxGraphics.getCamera)
    this.tiledMapRenderer.render(Array(0, 1))
    if(GameWindow.selectedEntity.isDefined){
      this.tiledMapRenderer.render(Array(2))
    }
  }
}

object Grid {
  val tileSize: Int = 32

  var walkableArray: Array[Array[Boolean]] = _

  def setWalkableArray(tiledMap: TiledMap): Unit = {
    val tiledLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]
    val width: Int = tiledLayer.getWidth
    val height: Int = tiledLayer.getHeight
    val walkableArray: Array[Array[Boolean]] = Array.ofDim(width, height)

    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val tile = tiledLayer.getCell(x, y).getTile
        walkableArray(x)(y) = tile.getProperties.get("walkable").toString.toBoolean
      }
    }
    this.walkableArray = walkableArray
  }

  def isValidGriSquare(x: Int, y: Int): Boolean = {

    x >= 0 && y >= 0 && x < Grid.walkableArray.length && y < Grid.walkableArray(0).length && Grid.walkableArray(x)(y)
  }

  def isPixelWalkable(x: Float, y: Float): Boolean = {

    val xInGridCell: Int = x.toInt / this.tileSize
    val yInGridCell: Int = y.toInt / this.tileSize

    if(xInGridCell >= this.walkableArray.length || yInGridCell >= this.walkableArray(0).length || xInGridCell < 0 || yInGridCell < 0){
      return false
    }

    for(entity: Entity <- Entity.entitiesArray){

      val entityXInGridCell = entity.position.x.toInt / this.tileSize
      val entityYInGridCell = entity.position.y.toInt / this.tileSize

      println(s"Entity X in grid cell $entityXInGridCell")
      println(s"Entity Y in grid cell $entityYInGridCell")

      if(xInGridCell == entityXInGridCell && yInGridCell == entityYInGridCell){
        return false
      }
    }

    this.walkableArray(xInGridCell)(yInGridCell)
  }

  def isPixelWalkable(x: Float, y: Float ,entityy: Entity): Boolean = {

    val xInGridCell: Int = x.toInt / this.tileSize
    val yInGridCell: Int = y.toInt / this.tileSize

    if(xInGridCell >= this.walkableArray.length || yInGridCell >= this.walkableArray(0).length || xInGridCell < 0 || yInGridCell < 0){
      return false
    }

    for(entity: Entity <- Entity.entitiesArray){
      if(entity == entityy || entity == entityy.target){

      }else{
        val entityXInGridCell = entity.position.x.toInt / this.tileSize
        val entityYInGridCell = entity.position.y.toInt / this.tileSize

        if(xInGridCell == entityXInGridCell && yInGridCell == entityYInGridCell){
          return false
        }
      }
    }

    this.walkableArray(xInGridCell)(yInGridCell)
  }

}
