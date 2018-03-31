package Hierarchical.Agglomerative.clustering.dimensions


trait Dimension[A, P[_]] {
  def computeDistance(from: P[A], to: P[A])(implicit distanceType: DistanceType[A, P]): A
}

object Dimension {
  implicit def unidimensionalImplicit[A: Numeric]: Dimension[A, UnidimensionalPoint] =
    new Dimension[A, UnidimensionalPoint] {
      override def computeDistance(from: UnidimensionalPoint[A],
                                   to: UnidimensionalPoint[A])(implicit distanceType: DistanceType[A, UnidimensionalPoint]): A = {
        distanceType.computeDistance(from, to)
      }
    }

  implicit def bidimensionalImplicit[A: Numeric]: Dimension[A, BidimensionalPoint] =
    new Dimension[A, BidimensionalPoint] {
      override def computeDistance(from: BidimensionalPoint[A],
                                   to: BidimensionalPoint[A])(implicit distanceType: DistanceType[A, BidimensionalPoint]): A = {
        distanceType.computeDistance(from, to)
      }
    }
}