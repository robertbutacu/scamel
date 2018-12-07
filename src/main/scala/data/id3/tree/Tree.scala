package data.id3.tree

import data.id3.Subset

sealed trait Tree

case class Leaf[A](attribute: A) extends Tree

case class Node[A, B](attribute: B,
                      nodes: List[NodeConnection[A, B]] = List.empty,
                      leafs: List[LeafConnection[A]] = List.empty) extends Tree

object Leaf {
  def apply[A, B](subset: Subset[A, B]): Leaf[A] = {
    require(subset.conclusion.data.headOption.nonEmpty)
    new Leaf(subset.conclusion.data.head)
  }
}

object Node {
  def tryToFit[A, B](node: Node[A, B], value: A): Option[Tree] = {
    def tryToFitIntoNodes: Option[Tree] = node.nodes.find(nc => nc.arc == value).map(_.to)
    def tryToFitIntoLeafs: Option[Tree] = node.leafs.find(l => l.arc == value).map(_.to)

    tryToFitIntoLeafs orElse tryToFitIntoNodes
  }
}