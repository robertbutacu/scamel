package data.hierarchical.clustering.clusters

import cats.Monoid
import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait ClusterCentroid[P[_]] {
  def computeCentroid[A](cluster: Cluster[A, P])(implicit F: Fractional[A],
                                                 Monoid    : Monoid[P[A]]): P[A]
}

object ClusterCentroid {
  implicit def unidimensionalPointsCentroid: ClusterCentroid[UnidimensionalPoint] = new ClusterCentroid[UnidimensionalPoint] {
    override def computeCentroid[A](cluster   : Cluster[A, UnidimensionalPoint])
                                   (implicit F: Fractional[A],
                                    Monoid    : Monoid[UnidimensionalPoint[A]]): UnidimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      UnidimensionalPoint("Point " + cluster.name, F.div(sum.X, F.fromInt(cluster.points.length)))
    }
  }

  implicit def bidimensionalPointsCentroid: ClusterCentroid[BidimensionalPoint] = new ClusterCentroid[BidimensionalPoint] {
    override def computeCentroid[A](cluster   : Cluster[A, BidimensionalPoint])
                                   (implicit F: Fractional[A],
                                    Monoid    : Monoid[BidimensionalPoint[A]]): BidimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length

      BidimensionalPoint("Point " + cluster.name,
        F.div(sum.X, F.fromInt(numberOfPoints)),
        F.div(sum.Y, F.fromInt(numberOfPoints)))
    }
  }

  implicit def tridimensionalPointsCentroid: ClusterCentroid[TridimensionalPoint] = new ClusterCentroid[TridimensionalPoint] {
    override def computeCentroid[A](cluster   : Cluster[A, TridimensionalPoint])
                                   (implicit F: Fractional[A],
                                    Monoid    : Monoid[TridimensionalPoint[A]]): TridimensionalPoint[A] = {
      val sum = Monoid.combineAll(cluster.points)

      val numberOfPoints = cluster.points.length

      TridimensionalPoint("Point " + cluster.name,
        F.div(sum.X, F.fromInt(numberOfPoints)),
        F.div(sum.Y, F.fromInt(numberOfPoints)),
        F.div(sum.Z, F.fromInt(numberOfPoints)))
    }
  }
}