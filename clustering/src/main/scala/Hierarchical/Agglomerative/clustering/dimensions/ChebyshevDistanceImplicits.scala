package Hierarchical.Agglomerative.clustering.dimensions

object ChebyshevDistanceImplicits {
  implicit def chebyshev1D[A: Numeric]: DistanceType[A, UnidimensionalPoint] =
    (from: UnidimensionalPoint[A], to: UnidimensionalPoint[A]) => {
      implicitly[Numeric[A]].zero
    }

  implicit def chebyshev2D[A: Numeric]: DistanceType[A, BidimensionalPoint] =
    (from: BidimensionalPoint[A], to: BidimensionalPoint[A]) => {
      implicitly[Numeric[A]].zero
    }

  implicit def chebyshev3D[A: Numeric]: DistanceType[A, TridimensionalPoint] =
    (from: TridimensionalPoint[A], to: TridimensionalPoint[A]) => {
      implicitly[Numeric[A]].zero
    }
}
