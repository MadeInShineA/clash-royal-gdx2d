package clash_royal_ISC.spells

import clash_royal_ISC.cards.CardObject

abstract class Spell extends CardObject {
  val damage: Int

  override def spawn(): Unit = ???
}
