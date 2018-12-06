package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.dimensions.Distance
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.types.{Method, NewCluster}

import scala.annotation.tailrec
import scala.language.higherKinds

object ClusteringAlgorithm {
  def clusterize[A, P[_], D](clusters: List[Cluster[A, P]],
                             distanceType: D, method: Method)
                            (implicit distance: Distance[A, P, D],
                             centroidCalculator: ClusterCentroid[A, P],
                             ord: Ordering[A]): Cluster[A, P] = {
    @tailrec
    def go(clusters: List[Cluster[A, P]],
           method: Method,
           currentIndex: Int = 0)(implicit distance: Distance[A, P, D]): Cluster[A, P] = {
      require(clusters.nonEmpty)
      if (clusters.size == 1)
        clusters.head
      else {
        val next = method.formCluster(clusters, distanceType)

        def isMergedIntoANewCluster(c: Cluster[A, P]) = c == next.first || c == next.second

        def updateClusters(): List[Cluster[A, P]] =
          clusters.filterNot(isMergedIntoANewCluster) ++ List(NewCluster.createCluster(next, currentIndex))

        go(updateClusters(),
          method,
          currentIndex + 1)
      }
    }

    go(clusters, method)
  }
}
