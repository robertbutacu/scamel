package Hierarchical.Agglomerative.clustering.dimensions

import Hierarchical.Agglomerative.clustering.dimensions.points.Point

object Calculator {
  def computeDistance[A: Numeric,
                      P[_] <: Point[_],
                      D <: DistanceType](from: P[A],
                                         to: P[A],
                                         distanceCalculatorType: D)
                                        (implicit distanceType: Distance[A, P, D]): A = {
    distanceType.computeDistance(from, to, distanceCalculatorType)
  }
}
