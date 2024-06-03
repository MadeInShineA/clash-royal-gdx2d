package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTile, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class Grid {

  var tiledMap: TiledMap = _
  var tiledMapRenderer: OrthogonalTiledMapRenderer = _
  var tiledLayer: MapLayers = _



  def render(gdxGraphics: GdxGraphics): Unit = {
    tiledMapRenderer.setView(gdxGraphics.getCamera)
    tiledMapRenderer.render()
  }
}
