package Hierarchical.Agglomerative.clustering.dimensions

object DistanceCalculator {
  def computeDistance[A: Numeric, B[_] <: Point[_]](from: B[A], to: B[A])(implicit distanceType: DistanceType[A, B]): A = {
    distanceType.computeDistance(from, to)
  }
}
