package clash_royal_ISC.Utils

import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTile, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

object AStar {
  val forbiddenTiles: Set[Int] = Set(206, 130, 169, 151)

  def isWalkable(tile: TiledMapTile): Boolean = {
    if (tile == null) return false
    !forbiddenTiles.contains(tile.getId.toInt)
  }

  def convertMapToBoolArray(tiledMap: TiledMap): Array[Array[Boolean]] = {
    val tiledLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]
    val width: Int = (tiledLayer.getWidth * tiledLayer.getTileWidth).toInt
    val height: Int = (tiledLayer.getHeight * tiledLayer.getTileHeight).toInt
    val walkableArray = Array.ofDim[Boolean](width, height)

    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val tileX = x / tiledLayer.getTileWidth.toInt
        val tileY = y / tiledLayer.getTileHeight.toInt
        val tile = tiledLayer.getCell(tileX, tileY).getTile
        walkableArray(x)(y) = isWalkable(tile)
      }
    }
    walkableArray
  }

  var walkableArray: Array[Array[Boolean]] = _

  val directions = List(
    (0, 1), (1, 0), (0, -1), (-1, 0),
    (1, 1), (1, -1), (-1, 1), (-1, -1)
  )

  def heuristic(start: Node, goal: Node): Double = {
    Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y) // Manhattan distance
  }

  def isValidPixel(x: Int, y: Int): Boolean = {
    x >= 0 && y >= 0 && x < this.walkableArray.length && y < this.walkableArray(0).length && this.walkableArray(x)(y)
  }

  def findPath(start: (Int, Int), goal: (Int, Int)): List[(Int, Int)] = {
    val startNode = Node(start._1, start._2, 0, heuristic(Node(start._1, start._2, 0, 0, None), Node(goal._1, goal._2, 0, 0, None)), None)
    val goalNode = Node(goal._1, goal._2, 0, 0, None)

    val openSet = mutable.PriorityQueue(startNode)(Ordering.by(node => -node.f))
    val closedSet = mutable.Set[(Int, Int)]()

    while (openSet.nonEmpty) {
      val currentNode = openSet.dequeue()

      if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
        var path = List[(Int, Int)]()
        var node: Option[Node] = Some(currentNode)
        while (node.isDefined) {
          path = (node.get.x, node.get.y) :: path
          node = node.get.parent
        }
        return path
      }

      closedSet.add((currentNode.x, currentNode.y))

      for ((dx, dy) <- directions) {
        val newX = currentNode.x + dx
        val newY = currentNode.y + dy

        if (isValidPixel(newX, newY) && !closedSet.contains((newX, newY))) {
          val gCost = currentNode.g + 1
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