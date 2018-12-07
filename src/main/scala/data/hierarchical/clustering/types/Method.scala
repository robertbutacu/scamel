package data.hierarchical.clustering.types

import data.hierarchical.clustering.clusters.{Cluster, ClusterCentroid, NewCluster}
import data.hierarchical.clustering.distance.Distance

import scala.language.higherKinds

trait Method {
  def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                             (implicit distance: Distance[A, P, D],
                              centroidCalculator: ClusterCentroid[A, P],
                              ord: Ordering[A]): NewCluster[A, P]
}
