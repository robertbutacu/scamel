package data.hierarchical.clustering.clusters

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait ClusterCentroid[A, P[_]] {
  def computeCentroid(cluster: Cluster[A, P]): P[A]
}

//test
object ClusterCentroid {
  implicit def unidimensionalPointsCentroid: ClusterCentroid[Double, UnidimensionalPoint] = new ClusterCentroid[Double, UnidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, UnidimensionalPoint]): UnidimensionalPoint[Double] = {
      val sum = cluster.points.foldRight(0.0)((curr, acc) => acc + curr.X)

      UnidimensionalPoint("Point " + cluster.name, sum / cluster.points.length)
    }
  }

  implicit def bidimensionalPointsCentroid: ClusterCentroid[Double, BidimensionalPoint] = new ClusterCentroid[Double, BidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, BidimensionalPoint]): BidimensionalPoint[Double] = {
      val (xSum, ySum) = cluster.points.foldRight((0.0, 0.0)){case (curr, (x, y)) => (x + curr.X, y + curr.Y)}

      val numberOfPoints = cluster.points.length
      BidimensionalPoint("Point " + cluster.name, xSum / numberOfPoints, ySum / numberOfPoints)
    }
  }

  implicit def tridimensionalPointsCentroid: ClusterCentroid[Double, TridimensionalPoint] = new ClusterCentroid[Double, TridimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, TridimensionalPoint]): TridimensionalPoint[Double] = {
      val sum = cluster.points.foldRight((0.0, 0.0, 0.0)) {
        case (curr, (x, y, z)) =>
          (x + curr.X, y + curr.Y, z + curr.Z)
      }

      val numberOfPoints = cluster.points.length

      TridimensionalPoint("Point " + cluster.name,
        sum._1 / numberOfPoints,
        sum._2 / numberOfPoints,
        sum._3 / numberOfPoints)
    }
  }
}