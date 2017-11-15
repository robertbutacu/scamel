package Hierarchical.Agglomerative.Clustering.Single.Linkage

import Hierarchical.Agglomerative.Clustering.{Connection, Node, Point}

/*
  Use Euclidean Distance, and draw the dendrogram.

  1. Calculate the Euclidian Distance,

  2. Create the Distance Matrix
      d(x, y) = rad( (x.x - y.y)^2 + (x.y - y.y) ^ 2 )

  3. From the Distance Matrix, get minimum Element and form a Dendogram.

  4. Recalculate the Distance.
    To Update the Distance Matrix  => Min[Dist((points chosen at step 3), referencePoint)]
                                      => Min(dist(point1, referencePoint), dist(point2, referencePoint)]

    Reference point - the rest of the points in the set.
 */

object HAC {
  def apply(input: List[Either[Point, Node]]): Node = {
    def go(points: List[Either[Point, Node]], clusteringLevel: Int = 0): Node = {
      val pointsWithIndex = points.zipWithIndex

      val distanceMatrix = for {
        firstPoint <- pointsWithIndex
        secondPoint <- pointsWithIndex.slice(firstPoint._2 + 1, pointsWithIndex.length)
      } yield (firstPoint, secondPoint, computeDistance(firstPoint._1, secondPoint._1))


      Node("random", Connection(Left(Point("x", 0.0, 0.0)), Left(Point("x", 0.0, 0.0))), Point("x", 0, 0), 0)
    }

    go(input)
  }

  private def computeDistance(from: Either[Point, Node], to: Either[Point, Node]): Double = {
    from match {
      case Left(point) => to match {
        case Left(otherPoint) => distance(point, otherPoint)
        case Right(node)      => distance(point, node.coordinates)
      }
      case Right(node) => to match {
        case Left(point)      => distance(node.coordinates, point)
        case Right(otherNode) => distance(node.coordinates, otherNode.coordinates)
      }
    }
  }

  private def distance(A: Point, B: Point): Double =
    round(
      Math.pow(
        Math.pow(A.X - B.X, 2) + Math.pow(A.Y - B.Y, 2)
        , 0.5)
    )



  private def round(x: Double): Double =
    (x * 1000).floor / 1000
}
