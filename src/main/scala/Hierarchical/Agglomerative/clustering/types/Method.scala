package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.Distance
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid

import scala.language.higherKinds

trait Method {
  def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                             (implicit distance: Distance[A, P, D],
                              centroidCalculator: ClusterCentroid[A, P],
                              ord: Ordering[A]): NewCluster[A, P]
}
