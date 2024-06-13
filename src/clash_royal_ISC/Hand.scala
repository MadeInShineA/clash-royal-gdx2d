package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Hand.{P1_POSITION, P2_POSITION, SIZE, WIDTH, ENTITIES_ARRAY}
import clash_royal_ISC.Player.PLAYERS_ARRAY
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

  val POSITION: Vector2 = (if(PLAYERS_ARRAY.isEmpty) P1_POSITION else P2_POSITION)

  val ENTITIES: ArrayBuffer[Entity with Deployable] = this.createHand()

  def createHand(): ArrayBuffer[Entity with Deployable] = {
    val res: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

    for(i <- 0 until SIZE){
      val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
      //      res += this.AVAILABLE_ENTITIES(randomIndex).clone()
      val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
      newEntity.position = new Vector2(i * (WIDTH / SIZE), this.POSITION.y)
      res += newEntity
      ENTITIES_ARRAY += newEntity
    }
    res
  }

  def addEntity(index: Int): Unit = {
    assert(index <= SIZE - 1)
    val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
    val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
    newEntity.position = new Vector2(index * (WIDTH / SIZE), this.POSITION.y)

    this.ENTITIES.insert(index, newEntity)
    ENTITIES_ARRAY += newEntity
    assert(this.ENTITIES.length <= SIZE)
  }

  def removeEntity(entity: Entity with Deployable): Int = {
    assert(this.ENTITIES.contains(entity))
    val index: Int = this.ENTITIES.indexOf(entity)
    this.ENTITIES -= entity
    ENTITIES_ARRAY -= entity
    index
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    for((entity ,index) <- ENTITIES.zipWithIndex){
      val xPosition: Float = (index) * (WIDTH / SIZE)
      val yPosition: Float = this.POSITION.y
      entity.drawHandSprite(xPosition, yPosition, gdxGraphics)
      if(this.player.currentElixir < entity.COST){
        gdxGraphics.drawPicture(xPosition + Grid.TILE_SIZE / 2, yPosition + Grid.TILE_SIZE / 2, new BitmapImage("res/map/transparentRedFilter.png"))
      }
      if(GameWindow.selectedEntity.isDefined && entity == GameWindow.selectedEntity.get){
        gdxGraphics.drawPicture(xPosition + Grid.TILE_SIZE / 2, yPosition + Grid.TILE_SIZE / 2, new BitmapImage("res/map/transparentFilter.png"))
      }
    }
  }
}

object Hand {

  val ENTITIES_ARRAY: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

  val SIZE: Int = 5

  val P1_POSITION: Vector2 = new Vector2(0, Grid.TILE_SIZE)
  val P2_POSITION :Vector2 = new Vector2(0, 30 * Grid.TILE_SIZE)

  val HEIGHT: Int = 1 * Grid.TILE_SIZE
  val WIDTH: Int = GameWindow.WINDOW_WIDTH


  def getEntityAtPosition(x: Float, y: Float): Option[Entity with Deployable] = {
    for(entity: Entity with Deployable <- ENTITIES_ARRAY){
      if(entity.position.x <= x && entity.position.y <= y && entity.position.x + entity.HAND_SPRITE_WIDTH >= x && entity.position.y + entity.HAND_SPRITE_HEIGHT >= y ){
        return Option(entity)
      }
    }
    None
  }
}
