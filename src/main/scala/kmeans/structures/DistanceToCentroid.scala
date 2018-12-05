package kmeans.structures

import Hierarchical.Agglomerative.clustering.dimensions.{Distance, EuclideanDistance}

case class DistanceToCentroid[P[_], A](point: P, centroid: Centroid[P, A], distance: A)

object DistanceToCentroid {
  def createCentroid[P[_], A](p: P[A], c: Centroid[P, A])(implicit distance: Distance[A, P, EuclideanDistance.type]): DistanceToCentroid[P, A] = {
    DistanceToCentroid(p, c, distance.computeDistance(p, c.point))
  }
}
