package Hierarchical.Agglomerative.clustering.dimensions

object DistanceCalculator {
  def computeDistance[A](from: Point[A], to: Point[A])(implicit dimension: Dimension[A, Point]): A = {
    dimension.computeDistance(from, to)
  }
}
