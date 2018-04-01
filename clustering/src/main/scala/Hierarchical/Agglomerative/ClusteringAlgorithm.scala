package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.dimensions.{DistanceType, Point}
import Hierarchical.Agglomerative.clustering.distances.Distance
import Hierarchical.Agglomerative.clustering.types.{Method, NewCluster}

import scala.annotation.tailrec

object ClusteringAlgorithm {
  def clusterize[A: Numeric, P[_] <: Point[_]](clusters: List[Cluster[A, P]],
                                               method: Method)(implicit distanceType: DistanceType): Cluster[A, P] = {
    @tailrec
    def go(clusters: List[Cluster[A, P]], method: Method, currentIndex: Int = 0): Cluster[A, P] = {
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
