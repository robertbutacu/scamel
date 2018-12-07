package data.hierarchical.clustering.distance

trait DistanceType

case object EuclideanDistance extends DistanceType
case object ChebyshevDistance extends DistanceType
case object ManhattanDistance extends DistanceType
