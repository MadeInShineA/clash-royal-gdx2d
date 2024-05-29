package clash_royal_ISC

import clash_royal_ISC.entities.Entity
import clash_royal_ISC.entities.minions.TestMinion

object Main extends App {

  new TestMinion
  new TestMinion

  println(Entity.entitiesArray.mkString(","))
  Entity.entitiesArray(0).takeDamage(5)
  println("Minion0 took 5 damage")

  println(Entity.entitiesArray.mkString(","))


  Entity.entitiesArray(0).takeDamage(5)
  println("Minion0 took 5 damage")

  println(Entity.entitiesArray.mkString(","))

  new GameWindow


}
