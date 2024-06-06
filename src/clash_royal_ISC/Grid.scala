package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
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
      this.tiledMapRenderer.render(Array(2))
    }
  }
}

object Grid {
  val tileSize: Int = 32
}
