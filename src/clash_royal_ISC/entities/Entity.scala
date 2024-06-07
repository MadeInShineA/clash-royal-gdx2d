package clash_royal_ISC.entities

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Player
import clash_royal_ISC.entities.Entity.entitiesArray
import clash_royal_ISC.entities.minions.Minion
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

abstract class Entity(val player: Player) extends DrawableObject {


  val spriteSheet: Spritesheet
  val spriteWidth: Int
  val spriteHeight: Int
  var textureY: Int


  var position: Vector2 = _

  val MAX_HEALTH: Int
  var health: Float
  val range: Int
  val attackSpeed: Int
  val attackDamage: Float

  val healthSpriteSheet: Spritesheet = new Spritesheet("res/sprites/minions/health-bar.png", 80, 11);

  var target: Entity = _

  var animationFramesCount: Int = 0
  var currentAnimationFrame: Int = 0
  val animationFramesAmount: Int
  val animationFramesWaitAmount: Int

  object Direction extends Enumeration {
    val UP, DOWN, LEFT, RIGHT = Value
  }

  def spawn(position: Vector2): Unit = {
    this.position = position
    entitiesArray += this
  }

  def dies(): Unit = {
    new SoundSample("res/sounds/death.mp3").play()
    entitiesArray -= this
  }

  def takeDamage(damageAmount: Float): Unit = {
    this.health -= damageAmount
    if (this.health <= 0) {
      this.dies()
    }
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

  def setDirection(): Unit = {
    val deltaX: Float = this.target.position.x - this.position.x
    val deltaY: Float = this.target.position.y - this.position.y


    if(Math.abs(deltaX) > Math.abs(deltaY)){
      if(deltaX > 0){
        turn(Direction.RIGHT)
      }else{
        turn(Direction.LEFT)
      }
    }else{
      if(deltaY > 0){
        turn(Direction.UP)
      }else{
        turn(Direction.DOWN)
      }
    }
  }

  def setTarget(): Unit = {

    val ennemiEntities = entitiesArray.filter(_.player != this.player)

    // The array shouldn't ever be empty when the function is called since each player has at least 1 Tower
    assert(ennemiEntities.nonEmpty)

    this.target = ennemiEntities.minBy(_.position.dst(this.position))

  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    if(this.animationFramesCount % this.animationFramesWaitAmount == 0){
      this.currentAnimationFrame = (this.currentAnimationFrame + 1) % this.animationFramesAmount
    }
//    println("Current animation frame : " + this.currentAnimationFrame)
//    println("Animaiton frames count " + this.animationFramesCount)
//    println("Animation frames wait amount " + this.animationFramesWaitAmount)
    this.animationFramesCount += 1

    gdxGraphics.draw(this.spriteSheet.sprites(this.textureY)(currentAnimationFrame), this.position.x, this.position.y)

    val healthPercentage: Float = this.health * 100 / this.MAX_HEALTH

    val healthSprite = Math.min(3, Math.max(0, healthPercentage.toInt / 25))
    gdxGraphics.draw(this.healthSpriteSheet.sprites(healthSprite)(0), this.position.x - this.spriteWidth / 2, this.position.y + 50)
  }

//  override def draw(gdxGraphics: GdxGraphics): Unit = {
//    gdxGraphics.drawFilledCircle(this.position.x, this.position.y, 20f, Color.BLACK)
//  }

  def targetIsInRange(): Boolean = {
    if (this.position.dst(target.position) <= range)  true else false
  }

}

object Entity {
  val entitiesArray: ArrayBuffer[Entity] = new ArrayBuffer()


  var updateCounter: Int = 0

  def updateEntities(gdxGraphics: GdxGraphics, deltaTime: Float): Unit = {
    var entityCounter: Int = 0
    while (entityCounter < entitiesArray.length){
      val entity: Entity = entitiesArray(entityCounter)
      entity.setTarget()

      // TODO Add a function inside the Minion class instead
      entity match {
        case minion: Minion =>

          if (minion.path == null) {
            println("Setting path")
            minion.setPath()
          }

          for (pathPoint <- minion.path) {
            gdxGraphics.drawFilledCircle(pathPoint._1, pathPoint._2, 10, Color.CHARTREUSE)
          }
          minion.move(deltaTime)
        case _ =>
      }
      // entity.turn
      entity.setDirection()
      entity.draw(gdxGraphics)
      if(entity.targetIsInRange() && this.updateCounter % entity.attackSpeed * 10 == 0){
        entity.target.takeDamage(entity.attackDamage)
      }
      entityCounter += 1
      }
  }
//    for(entity <- entitiesArray){

//  }

  def setEntitiesPathAsync(): Unit = {
    for (entity: Entity <- entitiesArray) {
      entity match {
        case minion: Minion =>
          minion.setPath()
        case _ =>
      }
    }
  }
}
