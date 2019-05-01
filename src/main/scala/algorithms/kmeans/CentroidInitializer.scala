package algorithms.kmeans

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}
import data.kmeans.structures.Centroid

import scala.language.higherKinds
import scala.util.Random

trait CentroidInitializer[P[_]] {
  def initialize[A](points: List[P[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[P, A]]
}

object CentroidInitializer {
  val tridimensionalRandomInitializer: CentroidInitializer[TridimensionalPoint] = new CentroidInitializer[TridimensionalPoint] {
    override def initialize[A](points: List[TridimensionalPoint[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[TridimensionalPoint, A]] = {
      (1 to number)
        .map { p =>
          Centroid("Centroid " + p, points(Random.nextInt(points.length)))
        }.toList
    }
  }

  val bidimensionalRandomInitializer: CentroidInitializer[BidimensionalPoint] = new CentroidInitializer[BidimensionalPoint] {
    def initialize[A](points: List[BidimensionalPoint[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[BidimensionalPoint, A]] = {
      (1 to number)
        .map { p =>
         Centroid("Centroid " + p, points(Random.nextInt(points.length)))
        }.toList
    }
  }

  val unidimensionalRandomInitializer: CentroidInitializer[UnidimensionalPoint] = new CentroidInitializer[UnidimensionalPoint] {
    override def initialize[A](points: List[UnidimensionalPoint[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[UnidimensionalPoint, A]] = {
      (1 to number)
        .map { p =>
          Centroid("Centroid " + p, points(Random.nextInt(points.length)))
        }.toList
    }
  }
}