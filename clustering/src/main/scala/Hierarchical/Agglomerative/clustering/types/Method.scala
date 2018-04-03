package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.dimensions.points.Point
import Hierarchical.Agglomerative.clustering.dimensions.{Distance, DistanceType}

trait Method {

  def formCluster[A: Numeric, P[_] <: Point[_], D <: DistanceType](clusters: List[Cluster[A, P]], distanceType: D)
                                                                  (implicit distance: Distance[A, P, D],
                                                                   centroidCalculator: ClusterCentroid[A, P]): NewCluster[A, P]

}
