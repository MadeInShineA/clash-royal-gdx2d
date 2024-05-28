package clash_royal_ISC

import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class Grid {
  val tiledMap = new TmxMapLoader().load("res/map.tmx")
  val tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)
  val tiledLayer =  tiledMap.getLayers

  tiledMapRenderer.render()
}
