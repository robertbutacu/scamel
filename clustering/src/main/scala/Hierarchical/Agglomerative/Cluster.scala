package Hierarchical.Agglomerative

case class Point(name: String, X: Double, Y: Double)

case class Cluster(points: List[Point],
                   creationIndex: Int = 0,
                   leftCluster: Option[Cluster] = None,
                   rightCluster: Option[Cluster] = None) {
  val name: String = points.foldLeft("")((res, p) => res + " " + p.name)

  def createCluster(other: Cluster, creationIndex: Int): Cluster =
    Cluster(this.points ++ other.points,
      creationIndex,
      Some(this),
      Some(other))

  def prettyPrinter(): Unit = {
    def go(curr: Cluster, tabs: Int = 0): Unit = {
      println(s"${"\t" * tabs} Cluster line $tabs: ${curr.name}")

      curr.leftCluster.foreach(go(_, tabs + 1))
      curr.rightCluster.foreach(go(_, tabs + 1))
    }

    go(this)
  }
}
