package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.dimensions.{Calculator, Distance, DistanceType}
import Hierarchical.Agglomerative.clustering.dimensions.points.Point

case object AverageLinkage extends Method {
  override def formCluster[A: Numeric, P[_] <: Point[_], D <: DistanceType](clusters: List[Cluster[A, P]], distanceType: D)
                                                        (implicit distance: Distance[A, P, D],
                                                         centroidCalculator: ClusterCentroid[A, P]): NewCluster[A, P] = {
    def createCentroid(cluster: Cluster[A, P])(implicit centroidCalculator: ClusterCentroid[A, P]): P[A] = {
     centroidCalculator.computeCentroid(cluster)
    }

    def computeDistance(from: (Cluster[A, P], P[A]),
                        to: (Cluster[A, P], P[A]))
                       (implicit distance: Distance[A, P, D]): A =
      Calculator.computeDistance(from._2, to._2, distanceType)

    val clustersWithCentroids = clusters zip (clusters map createCentroid)

    val possibleClusters = for {
      from <- clustersWithCentroids
      to <- clustersWithCentroids filterNot (_ == from)
    } yield (from, to, computeDistance(from, to))

    val nextCluster = possibleClusters.minBy(_._3)
    NewCluster[A, P](nextCluster._1._1, nextCluster._2._1, nextCluster._3)
  }
}
