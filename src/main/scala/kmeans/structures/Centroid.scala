package kmeans.structures

case class Centroid[P[_], A](name: String, point: P[A])