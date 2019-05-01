package algorithms.kmeans

import algorithms.kmeans.KMeans.Coordinate
import common.data.BidimensionalPoint
import data.kmeans.structures.Centroid

import scala.language.higherKinds
import scala.util.Random

trait CentroidInitializer[P[_]] {
  def initialize[A](points: List[P[A]], number: Int)(implicit frac: Fractional[A]): List[Centroid[P, A]]
}

object CentroidInitializer {
  val bidimensionalRandomInitializer: CentroidInitializer[BidimensionalPoint] = new CentroidInitializer[BidimensionalPoint] {
    def initialize[A](points: List[BidimensionalPoint[A]], number: Int)(implicit frac: Fractional[A]): List[Centroid[BidimensionalPoint, A]] = {
      val min: Coordinate[A] = Coordinate(points.minBy(_.X).X, points.minBy(_.Y).Y)

      val max: Coordinate[A] = Coordinate(points.maxBy(_.X).X, points.maxBy(_.Y).Y)

      (1 to number)
        .map { p =>
         /* println(frac.toInt(frac.plus(frac.minus(max.X, min.X), min.X)))
         Centroid("Centroid " + p,
            //TODO this is shit -> maybe write an own typeclass which converts to A ?
            BidimensionalPoint("Point " + p,
              frac.fromInt(Random.nextDouble(frac.toDouble(frac.plus(frac.minus(max.X, min.X), min.X)))),
              frac.fromInt(Random.nextInt(frac.toInt(frac.plus(frac.minus(max.Y, min.Y), min.Y))))
            ))*/
         Centroid("Centroid " + p, points(Random.nextInt(points.length)))
        }.toList
    }
  }

  val kmeansBidimensionalInitializer: CentroidInitializer[BidimensionalPoint] = new CentroidInitializer[BidimensionalPoint] {
    override def initialize[A](points: List[BidimensionalPoint[A]], number: Int)(implicit frac: Fractional[A]): List[Centroid[BidimensionalPoint, A]] = {
      //@tailrec
      def go(centroids: List[Centroid[BidimensionalPoint, A]], points: List[BidimensionalPoint[A]], remainingCentroids: Int): List[Centroid[BidimensionalPoint, A]] = {
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
}