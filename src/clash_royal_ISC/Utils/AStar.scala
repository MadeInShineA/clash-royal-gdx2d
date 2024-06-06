package clash_royal_ISC.Utils

import clash_royal_ISC.Grid
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTile, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable


object AStar {
//  val forbiddenTiles: Set[Int] = Set(206, 130, 169, 151, 13, 170, 171)

  var walkableArray: Array[Array[Boolean]] = _

  def convertMapToBoolArray(tiledMap: TiledMap): Array[Array[Boolean]] = {
        val tiledLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]
        val width: Int = tiledLayer.getWidth
        val height: Int = tiledLayer.getHeight
        val walkableArray: Array[Array[Boolean]] = Array.ofDim(width, height)

        for (y <- 0 until height) {
          for (x <- 0 until width) {
            val tile = tiledLayer.getCell(x, y).getTile
            walkableArray(x)(y) = tile.getProperties.get("walkable").toString.toBoolean
          }
        }
        walkableArray
  }


  val directions = List(
    (0, 1), (1, 0), (0, -1), (-1, 0),
    (1, 1), (1, -1), (-1, 1), (-1, -1)
  )

  def heuristic(start: Node, goal: Node): Double = {
    Math.sqrt(Math.pow(start.x - goal.x, 2) + Math.pow(start.y - goal.y, 2))
  }

  def isValidPixel(x: Int, y: Int): Boolean = {
    x >= 0 && y >= 0 && x < this.walkableArray.length && y < this.walkableArray(0).length && this.walkableArray(x)(y)
  }

  def findPath(start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
    val startInWalkableGrid: (Int, Int) = (start._1 / Grid.tileSize, start._2 / Grid.tileSize)
    val goalInWalkableGrid: (Int, Int) = (goal._1 / Grid.tileSize, goal._2 / Grid.tileSize)

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
          path = (node.get.x * Grid.tileSize + Grid.tileSize / 2, node.get.y * Grid.tileSize + Grid.tileSize / 2) :: path
          node = node.get.parent
        }
        return path.tail
      }

      closedSet.add((currentNode.x, currentNode.y))

      for ((dx, dy) <- directions) {
        val newX = currentNode.x + dx
        val newY = currentNode.y + dy

        if (isValidPixel(newX, newY) && !closedSet.contains((newX, newY))) {
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

//  def findPath(start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
//    val startNode = Node(start._1, start._2, 0, heuristic(Node(start._1, start._2, 0, 0, None), Node(goal._1, goal._2, 0, 0, None)), None)
//    val goalNode = Node(goal._1, goal._2, 0, 0, None)
//
//    val openSet = mutable.PriorityQueue(startNode)(Ordering.by(node => -node.f))
//    val closedSet = mutable.Set[(Int, Int)]()
//
//    while (openSet.nonEmpty) {
//      val currentNode = openSet.dequeue()
//
//      if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
//        var path = List[(Int, Int)]()
//        var node: Option[Node] = Some(currentNode)
//        while (node.isDefined) {
//          path = (node.get.x, node.get.y) :: path
//          node = node.get.parent
//        }
//        return path
//      }
//
//      closedSet.add((currentNode.x, currentNode.y))
//
//      for ((dx, dy) <- directions) {
//        val newX = currentNode.x + dx
//        val newY = currentNode.y + dy
//
//        if (isValidPixel(newX, newY) && !closedSet.contains((newX, newY))) {
//          val gCost = currentNode.g + (if (dx == 0 || dy == 0) 1 else Math.sqrt(2))
//          val hCost = heuristic(Node(newX, newY, 0, 0, None), goalNode)
//          val neighbor = Node(newX, newY, gCost, hCost, Some(currentNode))
//
//          if (!openSet.exists(n => n.x == newX && n.y == newY && n.f <= neighbor.f)) {
//            openSet.enqueue(neighbor)
//          }
//        }
//      }
//    }
//
//    List()
//  }
}