package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Hand.{P1_POSITION, P2_POSITION, SIZE}
import clash_royal_ISC.Player.playersArray
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Hand (player: Player) extends DrawableObject{

  val AVAILABLE_ENTITIES: Array[Entity with Deployable] = Array(new TestMinion(this.player))

  val entities: ArrayBuffer[Entity with Deployable] = this.createHand()

  def createHand(): ArrayBuffer[Entity with Deployable] = {
    val res: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

    for(i <- 0 until SIZE){
      val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
//      res += this.AVAILABLE_ENTITIES(randomIndex).clone()
      res += this.AVAILABLE_ENTITIES(randomIndex)
    }
    res
  }

  val position: Vector2 = (if(playersArray.length == 1) P1_POSITION else P2_POSITION)

  def addEntity(): Unit = {
    val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
//    this.entities += this.AVAILABLE_ENTITIES(randomIndex).clone()
    this.entities += this.AVAILABLE_ENTITIES(randomIndex)
    assert(this.entities.length <= SIZE)
  }

  def removeEntity(entity: Entity with Deployable): Unit = {
    assert(this.entities.contains(entity))
    this.entities -= entity
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    for((entity ,index) <- entities.zipWithIndex){
      val xPosition: Float = index * entity.handSpriteWidth
      val yPosition: Float = this.position.y
      entity.drawHandSprite(xPosition, yPosition, gdxGraphics)
    }
  }
}

object Hand {
  val SIZE: Int = 5

  val P1_POSITION: Vector2 = new Vector2(Grid.tileSize / 2, 0)
  val P2_POSITION :Vector2 = new Vector2(0, 30 * Grid.tileSize + Grid.tileSize / 2)

}
