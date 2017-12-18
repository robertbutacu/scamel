package Hierarchical.Agglomerative

case class Point(X: Double, Y: Double)

case class Cluster(points: List[Point],
                   creationIndex: Int = 0,
                   leftCluster: Option[Cluster] = None,
                   rightCluster: Option[Cluster] = None) {
  val name: String = points.reduce(_.toString ++ _.toString)

  def createCluster(other: Cluster, creationIndex: Int): Cluster =
    Cluster(this.points ++ other.points,
      creationIndex,
      Some(this),
      Some(other))
}
