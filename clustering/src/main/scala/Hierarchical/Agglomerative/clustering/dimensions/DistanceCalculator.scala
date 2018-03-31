package Hierarchical.Agglomerative.clustering.dimensions

object DistanceCalculator {
  def computeDistance[A: Numeric, B[_] <: Point[_]](from: B[A], to: B[A])(implicit dimension: Dimension[A, B]): A = {
    dimension.computeDistance(from, to)
  }
}
