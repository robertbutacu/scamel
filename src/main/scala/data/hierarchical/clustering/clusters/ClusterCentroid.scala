package data.hierarchical.clustering.clusters

import cats.Monoid
import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait ClusterCentroid[A, P[_]] {
  def computeCentroid(cluster: Cluster[A, P])(implicit Monoid: Monoid[P[A]]): P[A]
}

object ClusterCentroid {
  implicit def unidimensionalPointsCentroid: ClusterCentroid[Double, UnidimensionalPoint] = new ClusterCentroid[Double, UnidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, UnidimensionalPoint])(implicit Monoid: Monoid[UnidimensionalPoint[Double]]): UnidimensionalPoint[Double] = {
      val sum = Monoid.combineAll(cluster.points)

      UnidimensionalPoint("Point " + cluster.name, sum.X / cluster.points.length)
    }
  }

  implicit def bidimensionalPointsCentroid: ClusterCentroid[Double, BidimensionalPoint] = new ClusterCentroid[Double, BidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, BidimensionalPoint])(implicit Monoid: Monoid[BidimensionalPoint[Double]]): BidimensionalPoint[Double] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length
      BidimensionalPoint("Point " + cluster.name, sum.X / numberOfPoints, sum.Y / numberOfPoints)
    }
  }

  implicit def tridimensionalPointsCentroid: ClusterCentroid[Double, TridimensionalPoint] = new ClusterCentroid[Double, TridimensionalPoint] {
    override def computeCentroid(cluster: Cluster[Double, TridimensionalPoint])(implicit Monoid: Monoid[TridimensionalPoint[Double]]): TridimensionalPoint[Double] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length

      TridimensionalPoint("Point " + cluster.name,
        sum.X / numberOfPoints,
        sum.Y / numberOfPoints,
        sum.Z / numberOfPoints)
    }
  }
}