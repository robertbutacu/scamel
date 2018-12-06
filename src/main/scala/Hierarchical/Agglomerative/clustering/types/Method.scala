package hierarchical.agglomerative.clustering.types

import hierarchical.agglomerative.Cluster
import hierarchical.agglomerative.clustering.dimensions.Distance
import hierarchical.agglomerative.clustering.dimensions.clusters.ClusterCentroid

import scala.language.higherKinds

trait Method {
  def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                             (implicit distance: Distance[A, P, D],
                              centroidCalculator: ClusterCentroid[A, P],
                              ord: Ordering[A]): NewCluster[A, P]
}
