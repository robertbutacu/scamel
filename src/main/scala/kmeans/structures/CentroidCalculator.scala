package kmeans.structures

import common.data.BidimensionalPoint

trait CentroidCalculator[P[_]] {
  def repositionCentroid[A](centroid: Centroid[P, A], points: List[P[A]])(implicit frac: Fractional[A]): Centroid[P, A]
}

object CentroidCalculator {
  implicit val bidimensionalPointImplicit: CentroidCalculator[BidimensionalPoint] = new CentroidCalculator[BidimensionalPoint] {
    override def repositionCentroid[A](centroid: Centroid[BidimensionalPoint, A],
                                       points: List[BidimensionalPoint[A]])(implicit frac: Fractional[A]): Centroid[BidimensionalPoint, A] = {
      val centroidCoordinates = points.reduce((p, c) => BidimensionalPoint(centroid.name, frac.plus(p.X, c.X), frac.plus(p.Y, c.Y)))

      Centroid(centroid.name, BidimensionalPoint("?????", frac.div(centroidCoordinates.X, frac.fromInt(points.length)), frac.div(centroidCoordinates.Y, frac.fromInt(points.length))))
    }
  }
}
