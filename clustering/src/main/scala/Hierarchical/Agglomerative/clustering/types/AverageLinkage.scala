package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.{Calculator, EuclideanDistance, Point, UnidimensionalPoint}

case object AverageLinkage extends Method {
  override def formCluster[A: Numeric, P[_] <: Point[_]](clusters: List[Cluster[A, P]]): NewCluster[A, P] = {
    def createCentroid(cluster: Cluster[A, P]): P[A] = {
     // val XAxisSum = cluster.points.foldRight(0.0) { (curr, acc) => acc + curr.X }
     // val YAxisSum = cluster.points.foldRight(0.0) { (curr, acc) => acc + curr.Y }

     // Point("Centroid " + cluster.name, XAxisSum / cluster.points.length, YAxisSum / cluster.points.length)
      new UnidimensionalPoint[A]("a", implicitly[Numeric[A]].zero)
    }

    def computeDistance(from: (Cluster[A, P], P[A]), to: (Cluster[A, P], P[A])): A =
      Calculator.computeDistance(from._2, to._2, EuclideanDistance)

    val clustersWithCentroids = clusters zip (clusters map createCentroid)

    val possibleClusters = for {
      from <- clustersWithCentroids
      to <- clustersWithCentroids filterNot (_ == from)
    } yield (from, to, computeDistance(from, to))

    val nextCluster = possibleClusters.minBy(_._3)
    NewCluster[A, P](nextCluster._1._1, nextCluster._2._1, nextCluster._3)
  }
}
