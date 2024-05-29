package clash_royal_ISC

import clash_royal_ISC.cards.Card

import scala.collection.mutable.ArrayBuffer

class Player(val playerID: Int) {
  var hand: ArrayBuffer[Card] = _
  var currentElixir: Int = 0
}
