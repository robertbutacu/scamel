package id3.data.tree

import id3.data.BestAttribute
import id3.algorithm.Id3.{getLeafs, getNodes}

case class Node[A: Ordering, B](attribute: B,
                                nodes: List[NodeConnection[A, B]] = List.empty,
                                leafs: List[LeafConnection[A]] = List.empty)
  extends Tree

object Node {
  def apply[A: Ordering, B](bestAttribute: BestAttribute[A, B]): Node[A, B] =
    Node(bestAttribute.attribute, getNodes(bestAttribute), getLeafs(bestAttribute))

  //TODO rename inner functions
  def tryToFit[A: Ordering, B](node: Node[A, B], value:  A): Option[Tree] = {
    def tryToFitIntoNodes(): Option[Tree] =
      node.nodes.find(nc => nc.arc == value) match {
        case None => None
        case Some(connection) => Some(connection.to)
      }


    def tryToFitIntoLeafs(): Option[Tree] =
      node.leafs.find(l => l.arc == value) match {
        case None => None
        case Some(connection) => Some(connection.to)
      }

    tryToFitIntoLeafs().orElse(tryToFitIntoNodes())
  }
}