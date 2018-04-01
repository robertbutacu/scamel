package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.{Calculator, DistanceType, Point}

trait Method {

  def formCluster[A: Numeric, P[_] <: Point[_]](clusters: List[Cluster[A, P]]): NewCluster[A, P]

  final def distanceBetweenPoints[A: Numeric, P[_] <: Point[_]](current: P[A],
                                                                other: P[A])
                                                               (implicit distanceType: DistanceType): A = {
    Calculator.computeDistance(current, other, distanceType)
  }
}
