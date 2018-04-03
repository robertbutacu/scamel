package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.dimensions.{Distance, DistanceType}
import Hierarchical.Agglomerative.clustering.dimensions.points.Point
import Hierarchical.Agglomerative.clustering.types.{Method, NewCluster}

import scala.annotation.tailrec

object ClusteringAlgorithm {
  def clusterize[A: Numeric, P[_] <: Point[_], D <: DistanceType](clusters: List[Cluster[A, P]], distanceType: D,
                                               method: Method)(implicit distance: Distance[A, P, D],
                                                               centroidCalculator: ClusterCentroid[A, P]): Cluster[A, P] = {
    @tailrec
    def go(clusters: List[Cluster[A, P]],
           method: Method,
           currentIndex: Int = 0)(implicit distance: Distance[A, P, D]): Cluster[A, P] = {
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
