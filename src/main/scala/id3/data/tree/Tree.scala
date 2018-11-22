package id3.data.tree

import id3.algorithm.Id3.{getLeafs, getNodes}
import id3.data.{BestAttribute, Subset}

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
  def apply[A: Ordering, B](bestAttribute: BestAttribute[A, B]): Node[A, B] =
    Node(bestAttribute.attribute, getNodes(bestAttribute), getLeafs(bestAttribute))

  def tryToFit[A, B](node: Node[A, B], value: A): Option[Tree] = {
    def tryToFitIntoNodes: Option[Tree] = node.nodes.find(nc => nc.arc == value).map(_.to)
    def tryToFitIntoLeafs: Option[Tree] = node.leafs.find(l => l.arc == value).map(_.to)

    tryToFitIntoLeafs orElse tryToFitIntoNodes
  }
}