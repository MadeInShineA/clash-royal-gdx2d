package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Hand.{HEIGHT, P1_POSITION, P2_POSITION, SIZE, WIDTH, entitiesArray}
import clash_royal_ISC.Player.playersArray
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.buildings.Soldier
import clash_royal_ISC.entities.minions.{Barbarian, Giant, Princess, Wizard}
import clash_royal_ISC.entities.traits.Deployable
import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import ch.hevs.gdx2d.components.bitmaps.BitmapImage


class Hand (player: Player) extends DrawableObject{

  val AVAILABLE_ENTITIES: Array[Entity with Deployable] = Array(
    new Wizard(this.player),
    new Giant(this.player),
    new Barbarian(this.player),
    new Soldier(this.player),
    new Princess(this.player)
  )


  val position: Vector2 = (if(playersArray.isEmpty) P1_POSITION else P2_POSITION)

  val entities: ArrayBuffer[Entity with Deployable] = this.createHand()

  def createHand(): ArrayBuffer[Entity with Deployable] = {
    val res: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

    for(i <- 0 until SIZE){
      val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
      //      res += this.AVAILABLE_ENTITIES(randomIndex).clone()
      val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
      newEntity.position = new Vector2(i * (WIDTH / SIZE), this.position.y)
      res += newEntity
      entitiesArray += newEntity
    }
    res
  }


  def addEntity(index: Int): Unit = {
    assert(index <= SIZE - 1)
    val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
    val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
    newEntity.position = new Vector2(index * (WIDTH / SIZE), this.position.y)

    this.entities.insert(index, newEntity)
    entitiesArray += newEntity
    assert(this.entities.length <= SIZE)
  }

  def removeEntity(entity: Entity with Deployable): Int = {
    assert(this.entities.contains(entity))
    val index: Int = this.entities.indexOf(entity)
    this.entities -= entity
    entitiesArray -= entity
    index
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    for((entity ,index) <- entities.zipWithIndex){
      val xPosition: Float = (index) * (WIDTH / SIZE)
      val yPosition: Float = this.position.y
      entity.drawHandSprite(xPosition, yPosition, gdxGraphics)
      if(this.player.currentElixir < entity.cost){
        gdxGraphics.drawPicture(xPosition + Grid.TILE_SIZE / 2, yPosition + Grid.TILE_SIZE / 2, new BitmapImage("res/map/transparentRedFilter.png"))
      }
      if(GameWindow.selectedEntity.isDefined && entity == GameWindow.selectedEntity.get){
        gdxGraphics.drawPicture(xPosition + Grid.TILE_SIZE / 2, yPosition + Grid.TILE_SIZE / 2, new BitmapImage("res/map/transparentFilter.png"))
      }
    }
  }
}

object Hand {
  val SIZE: Int = 5

  val P1_POSITION: Vector2 = new Vector2(0, Grid.TILE_SIZE)
  val P2_POSITION :Vector2 = new Vector2(0, 30 * Grid.TILE_SIZE)

  val HEIGHT: Int = 1 * Grid.TILE_SIZE
  val WIDTH: Int = GameWindow.WINDOW_WIDTH

  val entitiesArray: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

  def getEntityAtPosition(x: Float, y: Float): Option[Entity with Deployable] = {
    for(entity: Entity with Deployable <- entitiesArray){
      if(entity.position.x <= x && entity.position.y <= y && entity.position.x + entity.handSpriteWidth >= x && entity.position.y + entity.handSpriteHeight >= y ){
        return Option(entity)
      }
    }
    None
  }

}
