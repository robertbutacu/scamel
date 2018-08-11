package kmeans

import kmeans.KMeans.Coordinate
import kmeans.structures.{Centroid, Point}

import scala.annotation.tailrec
import scala.util.Random

trait MethodInitialization {
  def initialize(number: Int,
                 points: List[Point]): List[Centroid]
}

case object RandomInitialization extends MethodInitialization {
  override def initialize(number: Int, points: List[Point]): List[Centroid] = {
    val min: Coordinate = Coordinate(points.minBy(_.X).X.toInt, points.minBy(_.Y).Y.toInt)

    val max: Coordinate = Coordinate(points.maxBy(_.X).X.toInt, points.maxBy(_.Y).Y.toInt)

    (1 to number)
      .map { p =>
        Centroid("Centroid " + p,
          Random.nextInt(max.X - min.X) + min.X,
          Random.nextInt(max.Y - min.Y) + min.Y)
      }.toList
  }
}

case object KMeansPlusPlus extends MethodInitialization {
  override def initialize(number: Int, points: List[Point]): List[Centroid] = {
    //@tailrec
    def go(centroids: List[Centroid], points: List[Point], remainingCentroids: Int): List[Centroid] = {
      if(remainingCentroids == 0)
        centroids
      else {
        ???
      }
    }
    /*
    Choose one center uniformly at random from among the data points.
    For each data point x, compute D(x), the distance between x and the nearest center that has already been chosen.
    Choose one new data point at random as a new center, using a weighted probability distribution where a point x is chosen with probability proportional to D(x)2.
     */

    go(List.empty, points, number)
  }
}
