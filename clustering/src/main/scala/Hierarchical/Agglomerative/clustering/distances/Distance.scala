package Hierarchical.Agglomerative.clustering.distances

import Hierarchical.Agglomerative.Point

trait Distance {
  def computeDistance(A: Point, B: Point): Double
}
