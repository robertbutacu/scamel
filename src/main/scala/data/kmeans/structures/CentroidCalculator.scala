package data.kmeans.structures

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait CentroidCalculator[P[_]] {
  def repositionCentroid[A](centroid: Centroid[P, A], points: List[P[A]])(implicit frac: Fractional[A]): Centroid[P, A]
}

object CentroidCalculator {
  implicit val bidimensionalPointImplicit: CentroidCalculator[BidimensionalPoint] = new CentroidCalculator[BidimensionalPoint] {
    override def repositionCentroid[A](centroid: Centroid[BidimensionalPoint, A],
                                       points: List[BidimensionalPoint[A]])(implicit frac: Fractional[A]): Centroid[BidimensionalPoint, A] = {
      val centroidCoordinates = points.reduce((p, c) => BidimensionalPoint(centroid.name, frac.plus(p.X, c.X), frac.plus(p.Y, c.Y)))

      //TODO find a better name
      Centroid(centroid.name, BidimensionalPoint("?????", frac.div(centroidCoordinates.X, frac.fromInt(points.length)), frac.div(centroidCoordinates.Y, frac.fromInt(points.length))))
    }
  }

  implicit val tridimensionalPointImplicit: CentroidCalculator[TridimensionalPoint] = new CentroidCalculator[TridimensionalPoint] {
    override def repositionCentroid[A](centroid: Centroid[TridimensionalPoint, A], points: List[TridimensionalPoint[A]])(implicit frac: Fractional[A]): Centroid[TridimensionalPoint, A] = {
      val centroidCoordinates = points.reduce { (p, c) =>
        TridimensionalPoint(centroid.name,
          frac.plus(p.X, c.X), frac.plus(p.Y, c.Y), frac.plus(p.Z, c.Z))
      }

      //TODO find a better name
      Centroid(centroid.name,
        TridimensionalPoint("?????",
          frac.div(centroidCoordinates.X, frac.fromInt(points.length)),
          frac.div(centroidCoordinates.Y, frac.fromInt(points.length)),
          frac.div(centroidCoordinates.Z, frac.fromInt(points.length))))
    }
  }

  implicit val unidimensionalPointImplicit: CentroidCalculator[UnidimensionalPoint] = new CentroidCalculator[UnidimensionalPoint] {
    override def repositionCentroid[A](centroid: Centroid[UnidimensionalPoint, A], points: List[UnidimensionalPoint[A]])(implicit frac: Fractional[A]): Centroid[UnidimensionalPoint, A] = {
      val centroidCoordinates = points.reduce((p, c) => UnidimensionalPoint(centroid.name, frac.plus(p.X, c.X)))

      //TODO find a better name
      Centroid(centroid.name, UnidimensionalPoint("?????", frac.div(centroidCoordinates.X, frac.fromInt(points.length))))
    }
  }
}
