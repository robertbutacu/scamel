package Hierarchical.Agglomerative.clustering.dimensions

trait Distance[A, P[_], D <: DistanceType] {
  def computeDistance(from: P[A], to: P[A], distanceType: D): A
}

object Distance {
  implicit def euclidean1D[A: Numeric]: Distance[A, UnidimensionalPoint, EuclideanDistance.type] =
    (from: UnidimensionalPoint[A], to: UnidimensionalPoint[A], _: EuclideanDistance.type) => {
      //distance.computeDistance(from, to)
      //for now, just the euclidean distance
      val num = implicitly[Numeric[A]]

      // | |X| - |Y| |
      num.abs(num.minus(num.abs(from.X), num.abs(to.X)))
    }

  implicit def euclidean2D[A: Numeric]: Distance[A, BidimensionalPoint, EuclideanDistance.type] =
    (from: BidimensionalPoint[A], to: BidimensionalPoint[A], _: EuclideanDistance.type) => {
      val num = implicitly[Numeric[A]]

      //Math.sqrt(Math.pow(A.X - B.X, 2) + Math.pow(A.Y - B.Y, 2))
      num.zero
    }

  implicit def euclidean3D[A: Numeric]: Distance[A, TridimensionalPoint, EuclideanDistance.type] =
    (from: TridimensionalPoint[A], to: TridimensionalPoint[A], _: EuclideanDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def manhattan1D[A: Numeric]: Distance[A, UnidimensionalPoint, ManhattanDistance.type] =
    (from: UnidimensionalPoint[A], to: UnidimensionalPoint[A], _: ManhattanDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def manhattan2D[A: Numeric]: Distance[A, BidimensionalPoint, ManhattanDistance.type] =
    (from: BidimensionalPoint[A], to: BidimensionalPoint[A], _: ManhattanDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def manhattan3D[A: Numeric]: Distance[A, TridimensionalPoint, ManhattanDistance.type] =
    (from: TridimensionalPoint[A], to: TridimensionalPoint[A], _: ManhattanDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def chebyshev1D[A: Numeric]: Distance[A, UnidimensionalPoint, ChebyshevDistance.type] =
    (from: UnidimensionalPoint[A], to: UnidimensionalPoint[A], _: ChebyshevDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def chebyshev2D[A: Numeric]: Distance[A, BidimensionalPoint, ChebyshevDistance.type] =
    (from: BidimensionalPoint[A], to: BidimensionalPoint[A], _: ChebyshevDistance.type) => {
      implicitly[Numeric[A]].zero
    }

  implicit def chebyshev3D[A: Numeric]: Distance[A, TridimensionalPoint, ChebyshevDistance.type] =
    (from: TridimensionalPoint[A], to: TridimensionalPoint[A], _: ChebyshevDistance.type) => {
      implicitly[Numeric[A]].zero
    }
}