package Hierarchical.Agglomerative.clustering.distances

import Hierarchical.Agglomerative.Point

object DistanceImplicits {
  implicit def euclideanDistance: Distance =
    (A: Point, B: Point) => Math.sqrt(Math.pow(A.X - B.X, 2) + Math.pow(A.Y - B.Y, 2))

  implicit def chebyshevDistance: Distance =
    (A: Point, B: Point) => Math.abs(A.X - B.X)

  implicit def manhattanDistance: Distance =
    (A: Point, B: Point) => Math.abs(A.X - B.X) + Math.abs(A.Y - B.Y)
}