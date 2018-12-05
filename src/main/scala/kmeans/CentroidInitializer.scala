package kmeans

import common.data.BidimensionalPoint
import kmeans.KMeans.Coordinate
import kmeans.structures.Centroid

import scala.util.Random

trait CentroidInitializer[P[_]] {
  def initialize[A](points: List[P[A]], number: Int): List[Centroid[P, A]]
}

object CentroidInitializer {
  val bidimensionalInitializer = new CentroidInitializer[BidimensionalPoint] {
    def initialize[A](points: List[BidimensionalPoint[A]], number: Int): List[Centroid[BidimensionalPoint, A]] = {
      val min: Coordinate[A] = Coordinate(points.minBy(_.X).X, points.minBy(_.Y).Y)

      val max: Coordinate[A] = Coordinate(points.maxBy(_.X).X, points.maxBy(_.Y).Y)

      (1 to number)
        .map { p =>
          Centroid("Centroid " + p,
            BidimensionalPoint("Point " + p, Random.nextInt(max.X - min.X) + min.X, Random.nextInt(max.Y - min.Y) + min.Y))
        }.toList
    }
  }
}