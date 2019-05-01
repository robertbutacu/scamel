package data.kmeans.structures

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}
import scala.language.higherKinds
import cats.Monoid

trait CentroidCalculator[P[_]] {
  def repositionCentroid[A](cluster: Cluster[P, A])
                           (implicit F: Fractional[A],
                            Monoid: Monoid[P[A]]): Centroid[P, A]
}

object CentroidCalculator {
  implicit val bidimensionalPointImplicit: CentroidCalculator[BidimensionalPoint] = new CentroidCalculator[BidimensionalPoint] {
    override def repositionCentroid[A](cluster: Cluster[BidimensionalPoint, A])(implicit F: Fractional[A], Monoid: Monoid[BidimensionalPoint[A]]): Centroid[BidimensionalPoint, A] = {
      val centroidCoordinates = Monoid.combineAll(cluster.points)
      val pointsCount = F.fromInt(cluster.points.length)

      Centroid(cluster.centroid.name,
        BidimensionalPoint(centroidCoordinates.name,
          F.div(centroidCoordinates.X, pointsCount),
          F.div(centroidCoordinates.Y, pointsCount)))
    }
  }

  implicit val tridimensionalPointImplicit: CentroidCalculator[TridimensionalPoint] = new CentroidCalculator[TridimensionalPoint] {
    override def repositionCentroid[A](cluster: Cluster[TridimensionalPoint, A])(implicit F: Fractional[A], Monoid: Monoid[TridimensionalPoint[A]]): Centroid[TridimensionalPoint, A] = {
      val centroidCoordinates = Monoid.combineAll(cluster.points)
      val pointsCount = F.fromInt(cluster.points.length)

      Centroid(cluster.centroid.name,
        TridimensionalPoint(centroidCoordinates.name,
          F.div(centroidCoordinates.X, pointsCount),
          F.div(centroidCoordinates.Y, pointsCount),
          F.div(centroidCoordinates.Z, pointsCount)))

    }
  }

  implicit val unidimensionalPointImplicit: CentroidCalculator[UnidimensionalPoint] = new CentroidCalculator[UnidimensionalPoint] {
    override def repositionCentroid[A](cluster: Cluster[UnidimensionalPoint, A])(implicit F: Fractional[A], Monoid: Monoid[UnidimensionalPoint[A]]): Centroid[UnidimensionalPoint, A] = {
      val centroidCoordinates = Monoid.combineAll(cluster.points)
      val pointsCount = F.fromInt(cluster.points.length)

      Centroid(cluster.centroid.name,
        UnidimensionalPoint(centroidCoordinates.name,
          F.div(centroidCoordinates.X, pointsCount)))
    }
  }
}
