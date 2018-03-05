package id3.algorithm

import id3.data.tree.{Leaf, Node, Tree}

object Id3Classifier {
  def classify[A: Ordering, B](id3Tree: Node[A, B], toClassify: Map[B, A]): Option[A] = {
    def go(node: Option[Tree]): Option[A] = {
      node match {
        case None => None
        case Some(leaf: Leaf[A]) => Some(leaf.attribute)
        case Some(tree: Node[A, B]) => classify(tree, toClassify)
      }
    }

    toClassify.get(id3Tree.attribute) match {
      case None => None
      case Some(value) => go(Node.tryToFit(id3Tree, value))
    }
  }
}
