package clash_royal_ISC

import com.badlogic.gdx.InputAdapter

class MouseListener extends InputAdapter {
  var clickX: Float = 0
  var clickY: Float = 0

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    clickX = screenX
    // Invert Y coordinate because the origin is at the top-left corner
    clickY = GameWindow.WINDOW_HEIGHT - screenY
    println(s"Mouse clicked at: ($clickX, $clickY)")
    true
  }
}
