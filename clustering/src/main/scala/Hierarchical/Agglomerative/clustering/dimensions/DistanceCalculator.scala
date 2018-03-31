package Hierarchical.Agglomerative.clustering.dimensions

object DistanceCalculator {
  def computeDistance[A: Numeric, B[_] <: Point[_], D <: DistanceCalculatorType](from: B[A],
                                                    to: B[A],
                                                    distanceCalculatorType: D)
                                                   (implicit distanceType: DistanceType[A, B, D]): A = {
    distanceType.computeDistance(from, to, distanceCalculatorType)
  }
}
