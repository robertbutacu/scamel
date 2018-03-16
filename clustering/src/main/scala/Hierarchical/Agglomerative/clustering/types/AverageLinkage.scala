package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.clustering.distances.Distance
import Hierarchical.Agglomerative.{Cluster, Point}

case object AverageLinkage extends Method {
  override def formCluster(clusters: List[Cluster])(implicit distance: Distance): NewCluster = {
    def createCentroid(cluster: Cluster): Point = {
      val XAxisSum = cluster.points.foldRight(0.0) { (curr, acc) => acc + curr.X }
      val YAxisSum = cluster.points.foldRight(0.0) { (curr, acc) => acc + curr.Y }

      Point("Centroid " + cluster.name, XAxisSum / cluster.points.length, YAxisSum / cluster.points.length)
    }

    def computeDistance(from: (Cluster, Point), to: (Cluster, Point)): Double =
      distanceBetweenPoints(from._2, to._2)(implicitly[Distance])

    val clustersWithCentroids = clusters zip (clusters map createCentroid)

    val possibleClusters = for {
      from <- clustersWithCentroids
      to <- clustersWithCentroids filterNot (_ == from)
    } yield (from, to, computeDistance(from, to))

    val nextCluster = possibleClusters.minBy(_._3)
    NewCluster(nextCluster._1._1, nextCluster._2._1, nextCluster._3)
  }
}
