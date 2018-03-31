package Hierarchical.Agglomerative.clustering.dimensions

object Calculator {
  def computeDistance[A: Numeric,
                      B[_] <: Point[_],
                      D <: DistanceType](from: B[A],
                                         to: B[A],
                                         distanceCalculatorType: D)
                                        (implicit distanceType: Distance[A, B, D]): A = {
    distanceType.computeDistance(from, to, distanceCalculatorType)
  }
}
