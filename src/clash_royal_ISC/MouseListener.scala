package clash_royal_ISC

import clash_royal_ISC.entities.{Deployable, Entity}
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

class MouseListener extends InputAdapter {
  var clickX: Float = 0
  var clickY: Float = 0

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    clickX = screenX
    // Invert Y coordinate because the origin is at the top-left corner
    clickY = GameWindow.WINDOW_HEIGHT - screenY
    println(s"Mouse clicked at: ($clickX, $clickY)")
    val selectedEntity: Option[Entity with Deployable] = Hand.getEntityAtPosition(clickX, clickY)
    if(selectedEntity.isDefined){
      GameWindow.selectedEntity = Hand.getEntityAtPosition(clickX, clickY)
    }else if(GameWindow.selectedEntity.isDefined){
      val entity: Entity with Deployable = GameWindow.selectedEntity.get
      entity.player.deployEntity(entity,new Vector2(clickX, clickY))
      GameWindow.selectedEntity = None
    }


    true
  }
}
