package clash_royal_ISC

import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

class MouseListener extends InputAdapter {


  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    val clickX: Int = screenX
    // Invert Y coordinate because the origin is at the top-left corner
    val clickY: Int = GameWindow.WINDOW_HEIGHT - screenY

    val clickXGridCell: Int = clickX.toInt / Grid.TILE_SIZE
    val clickYGridCell: Int = clickY.toInt / Grid.TILE_SIZE

    val selectedEntity: Option[Entity with Deployable] = Hand.getEntityAtPosition(clickX, clickY)

    if(selectedEntity.isDefined){
      GameWindow.selectedEntity = Hand.getEntityAtPosition(clickX, clickY)
    }else if(GameWindow.selectedEntity.isDefined &&  GameWindow.selectedEntity.get.player.deployableArray(clickXGridCell)(clickYGridCell) ){
      val entity: Entity with Deployable = GameWindow.selectedEntity.get
      entity.player.deployEntity(entity,new Vector2(clickX, clickY))
      GameWindow.selectedEntity = None
    }
    true
  }
}
