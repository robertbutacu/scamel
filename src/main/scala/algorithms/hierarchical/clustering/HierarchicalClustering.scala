package algorithms.hierarchical.clustering

import cats.Monoid
import data.hierarchical.clustering.clusters.{Cluster, ClusterCentroid, NewCluster}
import data.hierarchical.clustering.distance.Distance
import data.hierarchical.clustering.types.Method

import scala.annotation.tailrec
import scala.language.higherKinds

object HierarchicalClustering {
  def apply[A, P[_], D](clusters: List[Cluster[A, P]], method: Method)
                       (implicit distance: Distance[A, P, D],
                        Monoid            : Monoid[P[A]],
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
        val next = method.formCluster(clusters)

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
