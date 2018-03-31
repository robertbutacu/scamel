package Hierarchical.Agglomerative.clustering.dimensions

trait Distance[A, P[_], D <: DistanceType] {
  def computeDistance(from: P[A], to: P[A], distanceType: D): A
}

object Distance {
  implicit def euclidean1D: Distance[Double, UnidimensionalPoint, EuclideanDistance.type] =
    (from: UnidimensionalPoint[Double], to: UnidimensionalPoint[Double], _: EuclideanDistance.type) => {
      //distance.computeDistance(from, to)
      //for now, just the euclidean distance


      // | |X| - |Y| |
      0.0
    }

  implicit def euclidean2D: Distance[Double, BidimensionalPoint, EuclideanDistance.type] =
    (from: BidimensionalPoint[Double], to: BidimensionalPoint[Double], _: EuclideanDistance.type) => {
      val num = implicitly[Numeric[Double]]

      //Math.sqrt(Math.pow(A.X - B.X, 2) + Math.pow(A.Y - B.Y, 2))
      num.zero
    }

  implicit def euclidean3D: Distance[Double, TridimensionalPoint, EuclideanDistance.type] =
    (from: TridimensionalPoint[Double], to: TridimensionalPoint[Double], _: EuclideanDistance.type) => {
      implicitly[Numeric[Double]].zero
    }

  implicit def manhattan1D: Distance[Double, UnidimensionalPoint, ManhattanDistance.type] =
    (from: UnidimensionalPoint[Double], to: UnidimensionalPoint[Double], _: ManhattanDistance.type) => {
      implicitly[Numeric[Double]].zero
    }

  implicit def manhattan2D: Distance[Double, BidimensionalPoint, ManhattanDistance.type] =
    (from: BidimensionalPoint[Double], to: BidimensionalPoint[Double], _: ManhattanDistance.type) => {
      implicitly[Numeric[Double]].zero
    }

  implicit def manhattan3D: Distance[Double, TridimensionalPoint, ManhattanDistance.type] =
    (from: TridimensionalPoint[Double], to: TridimensionalPoint[Double], _: ManhattanDistance.type) => {
      implicitly[Numeric[Double]].zero
    }

  implicit def chebyshev1D: Distance[Double, UnidimensionalPoint, ChebyshevDistance.type] =
    (from: UnidimensionalPoint[Double], to: UnidimensionalPoint[Double], _: ChebyshevDistance.type) => {
      implicitly[Numeric[Double]].zero
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