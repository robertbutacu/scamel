package Hierarchical.Agglomerative

import Hierarchical.Agglomerative.clustering.dimensions.Point


case class Cluster[A: Numeric, P[_] <: Point[_]](points: List[P[_]],
                   creationIndex: Int = 0,
                   leftCluster: Option[Cluster[A, P]] = None,
                   rightCluster: Option[Cluster[A, P]] = None) {
  val name: String = points.foldLeft("")((res, p) => res + " " + p.name)

  def prettyPrinter(): Unit = {
    def go(curr: Cluster[A, P[_]], tabs: Int = 0): Unit = {
      println(s"${"\t" * tabs} Cluster line $tabs: ${curr.name}")

      curr.leftCluster.foreach(go(_, tabs + 1))
      curr.rightCluster.foreach(go(_, tabs + 1))
    }

    go(this[A, P[_]])
  }
}
