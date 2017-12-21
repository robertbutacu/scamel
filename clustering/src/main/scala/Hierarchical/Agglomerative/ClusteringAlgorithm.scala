package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.types.Method

import scala.annotation.tailrec

object ClusteringAlgorithm {
  def clusterize(clusters: List[Cluster], method: Method): Cluster = {
    @tailrec
    def go(clusters: List[Cluster], method: Method, currentIndex: Int = 0): Cluster = {
      if (clusters.size == 1)
        clusters.head
      else {
        val next = method.formCluster(clusters)

        go(clusters.filterNot(c => c == next._1 || c == next._2) ++ List(next._1.createCluster(next._2, currentIndex)),
          method,
          currentIndex + 1)
      }
    }

    go(clusters, method)
  }
}
