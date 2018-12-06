package hierarchical.agglomerative.clustering.dimensions.clusters

import hierarchical.agglomerative.Cluster
import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait ClusterCentroid[A, P[_]] {
  def computeCentroid(cluster: Cluster[A, P]): P[A]
}

//test
object ClusterCentroid {
  implicit def unidimensionalPointsCentroid: ClusterCentroid[Double, UnidimensionalPoint] =
    (cluster: Cluster[Double, UnidimensionalPoint]) => {
      val sum = cluster.points.foldRight(0.0)((curr, acc) => acc + curr.X)

      UnidimensionalPoint("Point " + cluster.name, sum / cluster.points.length)
    }

  implicit def bidimensionalPointsCentroid: ClusterCentroid[Double, BidimensionalPoint] =
    (cluster: Cluster[Double, BidimensionalPoint]) => {
      val (xSum, ySum) = cluster.points.foldRight((0.0, 0.0)){case (curr, (x, y)) => (x + curr.X, y + curr.Y)}

      val numberOfPoints = cluster.points.length
      BidimensionalPoint("Point " + cluster.name, xSum / numberOfPoints, ySum / numberOfPoints)
    }

  implicit def tridimensionalPointsCentroid: ClusterCentroid[Double, TridimensionalPoint] =
    (cluster: Cluster[Double, TridimensionalPoint]) => {
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