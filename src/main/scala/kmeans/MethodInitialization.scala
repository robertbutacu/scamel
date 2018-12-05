package kmeans

import common.data.Point
import kmeans.KMeans.Coordinate
import kmeans.structures.Centroid

import scala.util.Random

trait MethodInitialization {
  def initialize[P[_], A](number: Int, points: List[P[A]])(implicit methodInitializer: CentroidInitializer[P]): List[Centroid[P, A]]
}

case object RandomInitialization extends MethodInitialization {
  override def initialize[P[_], A](number: Int, points: List[P[A]])
                                  (implicit methodInitializer: CentroidInitializer[P]): List[Centroid[P, A]] =
    methodInitializer.initialize(points, number)
}

case object KMeansPlusPlus extends MethodInitialization {
  override def initialize[P[_], A](number: Int, points: List[P[A]])(implicit methodInitialization: CentroidInitializer[P]): List[Centroid[P, A]] = {
    //@tailrec
    def go(centroids: List[Centroid[P, A]], points: List[P[A]], remainingCentroids: Int): List[Centroid[P, A]] = {
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
