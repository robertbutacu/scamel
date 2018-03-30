package Hierarchical.Agglomerative.clustering.dimensions

trait Point[A]

case class UnidimensionalPoint[A: Numeric](X: A) extends Point[A]
case class BidimensionalPoint[A: Numeric](X: A, Y: A) extends Point[A]
case class TridimensionalPoint[A: Numeric](X: A, Y: A, Z: A) extends Point[A]