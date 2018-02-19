package id3.data.tree

import id3.data.BestAttribute
import id3.algorithm.Id3.{getLeafs, getNodes}

case class Node[A: Ordering, B](attribute: B,
                                nodes: List[NodeConnection[A, B]] = List.empty,
                                leafs: List[LeafConnection[A]] = List.empty)

object Node {
  def apply[A: Ordering, B](bestAttribute: BestAttribute[A, B]): Node[A, B] =
    Node(bestAttribute.attribute, getNodes(bestAttribute), getLeafs(bestAttribute))
}