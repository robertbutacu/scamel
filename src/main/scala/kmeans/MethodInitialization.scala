package kmeans

import kmeans.KMeans.Coordinate
import kmeans.structures.{Centroid, Point}

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
  override def initialize(number: Int, points: List[Point]): List[Centroid] = ???
}
