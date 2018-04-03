package Hierarchical.Agglomerative.clustering.dimensions.clusters

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.points.{BidimensionalPoint, Point, TridimensionalPoint, UnidimensionalPoint}

trait ClusterCentroid[A, P[_] <: Point[_]] {
  def computeCentroid(cluster: Cluster[A, P]): P[A]
}

object ClusterCentroid {
  implicit def unidimensionalPointsCentroid: ClusterCentroid[Double, UnidimensionalPoint] =
    (cluster: Cluster[Double, UnidimensionalPoint]) => {
      val sum = cluster.points.foldRight(0.0)((curr, acc) => acc + curr.X)

      UnidimensionalPoint("Point " + cluster.name, sum / cluster.points.length)
    }

  implicit def bidimensionalPointsCentroid: ClusterCentroid[Double, BidimensionalPoint] =
    (cluster: Cluster[Double, BidimensionalPoint]) => {
      val sum = cluster.points.foldRight((0.0, 0.0))((curr, acc) => (acc._1 + curr.X, acc._2 + curr.Y))

      val numberOfPoints = cluster.points.length
      BidimensionalPoint("Point " + cluster.name, sum._1 / numberOfPoints, sum._2 / numberOfPoints)
    }

  implicit def tridimensionalPointsCentroid: ClusterCentroid[Double, TridimensionalPoint] =
    (cluster: Cluster[Double, TridimensionalPoint]) => {
      val sum = cluster.points.foldRight((0.0, 0.0, 0.0)) {
        (curr, acc) =>
          (acc._1 + curr.X, acc._2 + curr.Y, acc._3 + curr.Z)
      }

      val numberOfPoints = cluster.points.length

      TridimensionalPoint("Point " + cluster.name,
        sum._1 / numberOfPoints,
        sum._2 / numberOfPoints,
        sum._3 / numberOfPoints)

    }
}