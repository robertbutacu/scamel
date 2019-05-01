package algorithms.kmeans

import data.kmeans.structures.Centroid

import scala.language.higherKinds
import scala.util.Random

trait CentroidInitializer[P[_]] {
  def initialize[A](points: List[P[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[P, A]]
}

object CentroidInitializer {
  def randomInitializer[P[_]]: CentroidInitializer[P] = new CentroidInitializer[P] {
    override def initialize[A](points: List[P[A]], number: Int)(implicit F: Fractional[A]): List[Centroid[P, A]] = {
      (1 to number)
        .map { p =>
          Centroid("Centroid " + p, points(Random.nextInt(points.length)))
        }.toList
    }
  }
}