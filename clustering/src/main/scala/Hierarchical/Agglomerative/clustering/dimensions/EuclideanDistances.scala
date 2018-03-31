package Hierarchical.Agglomerative.clustering.dimensions

object EuclideanDistances {
  implicit def euclidean1D[A: Numeric]: DistanceType[A, UnidimensionalPoint] =
    (from: UnidimensionalPoint[A], to: UnidimensionalPoint[A]) => {
      //distance.computeDistance(from, to)
      //for now, just the euclidean distance
      val num = implicitly[Numeric[A]]

      // | |X| - |Y| |
      num.abs(num.minus(num.abs(from.X), num.abs(to.X)))
    }

  implicit def euclidean2D[A: Numeric]: DistanceType[A, BidimensionalPoint] =
    (from: BidimensionalPoint[A], to: BidimensionalPoint[A]) => {
      val num = implicitly[Numeric[A]]

      num.zero
    }
}
