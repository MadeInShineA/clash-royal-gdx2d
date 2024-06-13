package clash_royal_ISC.utils

import clash_royal_ISC.Grid
import clash_royal_ISC.entities.Entity

import scala.collection.mutable


object AStar {

  val directions = List(
    (0, 1), (1, 0), (0, -1), (-1, 0),
    (1, 1), (1, -1), (-1, 1), (-1, -1)
  )

  def heuristic(start: Node, goal: Node): Double = {
    Math.sqrt(Math.pow(start.x - goal.x, 2) + Math.pow(start.y - goal.y, 2))
  }

//  def findPath(start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
  def findPathWithoutColision(entity: Entity, start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
    val startInWalkableGrid: (Int, Int) = (start._1 / Grid.TILE_SIZE, start._2 / Grid.TILE_SIZE)
    val goalInWalkableGrid: (Int, Int) = (goal._1 / Grid.TILE_SIZE, goal._2 / Grid.TILE_SIZE)

    val startNode = Node(startInWalkableGrid._1, startInWalkableGrid._2, 0, heuristic(Node(startInWalkableGrid._1, startInWalkableGrid._2, 0, 0, None), Node(goalInWalkableGrid._1, goalInWalkableGrid._2, 0, 0, None)), None)
    val goalNode = Node(goalInWalkableGrid._1, goalInWalkableGrid._2, 0, 0, None)

    val openSet = mutable.PriorityQueue(startNode)(Ordering.by(node => -node.f))
    val closedSet = mutable.Set[(Int, Int)]()

    while (openSet.nonEmpty) {
      val currentNode = openSet.dequeue()

      if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
        var path = List[(Int, Int)]()
        var node: Option[Node] = Some(currentNode)
        while (node.isDefined) {
          path = (node.get.x * Grid.TILE_SIZE + Grid.TILE_SIZE / 2, node.get.y * Grid.TILE_SIZE + Grid.TILE_SIZE / 2) :: path
          node = node.get.parent
        }
        return path.tail
      }

      closedSet.add((currentNode.x, currentNode.y))

      for ((dx, dy) <- directions) {
        val newX = currentNode.x + dx
        val newY = currentNode.y + dy

        if (Grid.isPixelValidPathWithoutColision(newX * Grid.TILE_SIZE, newY * Grid.TILE_SIZE, entity) && !closedSet.contains((newX, newY))) {
          val gCost = currentNode.g + (if (dx == 0 || dy == 0) 1 else Math.sqrt(2))
          val hCost = heuristic(Node(newX, newY, 0, 0, None), goalNode)
          val neighbor = Node(newX, newY, gCost, hCost, Some(currentNode))

          if (!openSet.exists(n => n.x == newX && n.y == newY && n.f <= neighbor.f)) {
            openSet.enqueue(neighbor)
          }
        }
      }
    }

    List()
  }


  def findAbsolutePath(start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
    val startInWalkableGrid: (Int, Int) = (start._1 / Grid.TILE_SIZE, start._2 / Grid.TILE_SIZE)
    val goalInWalkableGrid: (Int, Int) = (goal._1 / Grid.TILE_SIZE, goal._2 / Grid.TILE_SIZE)

    val startNode = Node(startInWalkableGrid._1, startInWalkableGrid._2, 0, heuristic(Node(startInWalkableGrid._1, startInWalkableGrid._2, 0, 0, None), Node(goalInWalkableGrid._1, goalInWalkableGrid._2, 0, 0, None)), None)
    val goalNode = Node(goalInWalkableGrid._1, goalInWalkableGrid._2, 0, 0, None)

    val openSet = mutable.PriorityQueue(startNode)(Ordering.by(node => -node.f))
    val closedSet = mutable.Set[(Int, Int)]()

    while (openSet.nonEmpty) {
      val currentNode = openSet.dequeue()

      if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
        var path = List[(Int, Int)]()
        var node: Option[Node] = Some(currentNode)
        while (node.isDefined) {
          path = (node.get.x * Grid.TILE_SIZE + Grid.TILE_SIZE / 2, node.get.y * Grid.TILE_SIZE + Grid.TILE_SIZE / 2) :: path
          node = node.get.parent
        }
        return path.tail
      }

      closedSet.add((currentNode.x, currentNode.y))

      for ((dx, dy) <- directions) {
        val newX = currentNode.x + dx
        val newY = currentNode.y + dy

        if (!closedSet.contains((newX, newY))) {
          val gCost = currentNode.g + (if (dx == 0 || dy == 0) 1 else Math.sqrt(2))
          val hCost = heuristic(Node(newX, newY, 0, 0, None), goalNode)
          val neighbor = Node(newX, newY, gCost, hCost, Some(currentNode))

          if (!openSet.exists(n => n.x == newX && n.y == newY && n.f <= neighbor.f)) {
            openSet.enqueue(neighbor)
          }
        }
      }
    }

    List()
  }
}