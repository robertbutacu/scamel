package Hierarchical.Agglomerative.Clustering

case class Distance(from: Point, to: Point, distance: Double)

case class Connection[A, B](from: A <:< B, to : A <:< B)