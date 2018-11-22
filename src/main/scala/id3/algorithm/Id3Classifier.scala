package id3.algorithm

import id3.data.tree.{Leaf, Node}

object Id3Classifier {
  def classify[A: Ordering, B](id3Tree: Node[A, B], toClassify: Map[B, A]): Option[A] = {
    for {
      value      <- toClassify.get(id3Tree.attribute)
      fitValue   <- Node.tryToFit(id3Tree, value)
      classified <- fitValue match {
        case l: Leaf[A] => Some(l.attribute)
        case t: Node[A, B] => classify(t, toClassify)
      }
    } yield classified
  }
}
