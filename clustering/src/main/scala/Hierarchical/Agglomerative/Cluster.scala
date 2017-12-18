package Hierarchical.Agglomerative

case class Point(X: Double, Y: Double)

case class Cluster(points: List[Point],
                   leftCluster: Option[Cluster] = None,
                   rightCluster: Option[Cluster] = None) {
  val name: String = points.reduce(_.toString ++ _.toString)
}
