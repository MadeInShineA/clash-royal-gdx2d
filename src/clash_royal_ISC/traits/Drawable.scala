package clash_royal_ISC.traits

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.entities.Entity
import com.badlogic.gdx.math.Vector2

trait Drawable extends DrawableObject{

  val SPRITE_SHEET: Spritesheet
  val SPRITE_WIDTH: Int
  val SPRITE_HEIGHT: Int
  var textureY: Int

  var animationFramesCount: Int = 0
  var currentAnimationFrame: Int = 0
  val ANIMATION_FRAME_AMOUNT: Int
  val ANIMATION_FRAMES_WAIT_AMOUNT: Int

  var position: Vector2

  object Direction extends Enumeration {
    val UP, DOWN, LEFT, RIGHT = Value
  }

  def turn(direction: Direction.Value): Unit = {
    direction match {
      case Direction.UP => {
        this.textureY = 3
      }
      case Direction.DOWN => {
        this.textureY = 0
      }
      case Direction.LEFT => {
        this.textureY = 1
      }
      case Direction.RIGHT => {
        this.textureY = 2
      }
      case _ => {}
    }
  }

  def setDirection(target: Entity): Unit = {
    val deltaX: Float = target.position.x - this.position.x
    val deltaY: Float = target.position.y - this.position.y

    if(Math.abs(deltaX) > Math.abs(deltaY)){
      if(deltaX > 0){
        this.turn(Direction.RIGHT)
      }else{
        this.turn(Direction.LEFT)
      }
    }else{
      if(deltaY > 0){
        this.turn(Direction.UP)
      }else{
        this.turn(Direction.DOWN)
      }
    }
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    if(this.animationFramesCount % this.ANIMATION_FRAMES_WAIT_AMOUNT == 0){
      this.currentAnimationFrame = (this.currentAnimationFrame + 1) % this.ANIMATION_FRAME_AMOUNT
    }

    this.animationFramesCount += 1

    gdxGraphics.draw(this.SPRITE_SHEET.sprites(this.textureY)(currentAnimationFrame), this.position.x - this.SPRITE_WIDTH / 2, this.position.y)
  }

  }
