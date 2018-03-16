package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.distances.Distance
import Hierarchical.Agglomerative.clustering.types.{Method, NewCluster}

import scala.annotation.tailrec

object ClusteringAlgorithm {
  def clusterize(clusters: List[Cluster], method: Method)(implicit distance: Distance): Cluster = {
    @tailrec
    def go(clusters: List[Cluster], method: Method, currentIndex: Int = 0): Cluster = {
      if (clusters.size == 1)
        clusters.head
      else {
        val next = method.formCluster(clusters)

        def isMergedIntoANewCluster(c: Cluster) = c == next.first || c == next.second

        def updateClusters(): List[Cluster] =
          clusters.filterNot(isMergedIntoANewCluster) ++ List(NewCluster.createCluster(next, currentIndex))

        go(updateClusters(),
          method,
          currentIndex + 1)
      }
    }

    go(clusters, method)
  }
}
