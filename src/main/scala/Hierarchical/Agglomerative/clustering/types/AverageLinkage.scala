package hierarchical.agglomerative.clustering.types

import hierarchical.agglomerative.Cluster
import hierarchical.agglomerative.clustering.dimensions.Distance
import hierarchical.agglomerative.clustering.dimensions.clusters.ClusterCentroid

import scala.language.higherKinds

case object AverageLinkage extends Method {
  override def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                                                        (implicit distance: Distance[A, P, D],
                                                         centroidCalculator: ClusterCentroid[A, P],
                                                         ord: Ordering[A]): NewCluster[A, P] = {
    def createCentroid(cluster: Cluster[A, P])(implicit centroidCalculator: ClusterCentroid[A, P]): P[A] = {
     centroidCalculator.computeCentroid(cluster)
    }

    def computeDistance(from: (Cluster[A, P], P[A]),
                        to: (Cluster[A, P], P[A]))
                       (implicit distance: Distance[A, P, D]): A =
      distance.computeDistance(from._2, to._2)

    val clustersWithCentroids = clusters zip (clusters map createCentroid)

    val possibleClusters = for {
      from <- clustersWithCentroids
      to   <- clustersWithCentroids filterNot (_ == from)
    } yield (from, to, computeDistance(from, to))

    val nextCluster = possibleClusters.minBy(_._3)
    NewCluster[A, P](nextCluster._1._1, nextCluster._2._1, nextCluster._3)
  }
}
