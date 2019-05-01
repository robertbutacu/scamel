package data.hierarchical.clustering.clusters

import cats.Monoid
import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait ClusterCentroid[A, P[_]] {
  def computeCentroid(cluster: Cluster[A, P])(implicit Monoid: Monoid[P[A]]): P[A]
}

object ClusterCentroid {
  implicit def unidimensionalPointsCentroid[A](implicit F: Fractional[A]): ClusterCentroid[A, UnidimensionalPoint] = new ClusterCentroid[A, UnidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[A, UnidimensionalPoint])(implicit Monoid: Monoid[UnidimensionalPoint[A]]): UnidimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      UnidimensionalPoint("Point " + cluster.name, F.div(sum.X, F.fromInt(cluster.points.length)))
    }
  }

  implicit def bidimensionalPointsCentroid[A](implicit F: Fractional[A]): ClusterCentroid[A, BidimensionalPoint] = new ClusterCentroid[A, BidimensionalPoint] {
    override def computeCentroid(cluster: Cluster[A, BidimensionalPoint])(implicit Monoid: Monoid[BidimensionalPoint[A]]): BidimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length
      BidimensionalPoint("Point " + cluster.name,
        F.div(sum.X, F.fromInt(numberOfPoints)),
        F.div(sum.Y, F.fromInt(numberOfPoints)))
    }
  }

  implicit def tridimensionalPointsCentroid[A](implicit F: Fractional[A]): ClusterCentroid[A, TridimensionalPoint] = new ClusterCentroid[A, TridimensionalPoint] {
    override def computeCentroid(cluster: Cluster[A, TridimensionalPoint])(implicit Monoid: Monoid[TridimensionalPoint[A]]): TridimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length

      TridimensionalPoint("Point " + cluster.name,
        F.div(sum.X, F.fromInt(numberOfPoints)),
        F.div(sum.Y, F.fromInt(numberOfPoints)),
        F.div(sum.Z, F.fromInt(numberOfPoints)))
    }
  }
}