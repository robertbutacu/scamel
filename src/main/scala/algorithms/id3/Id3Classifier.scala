package algorithms.id3

import data.id3.tree.{Leaf, Node}

object Id3Classifier {
  def apply[A: Ordering, B](id3Tree: Node[A, B], toClassify: Map[B, A]): Option[A] = {
    for {
      value      <- toClassify.get(id3Tree.attribute)
      fitValue   <- Node.tryToFit(id3Tree, value)
      classified <- fitValue match {
        case l: Leaf[A]    => Some(l.attribute)
        case t: Node[A, B] => apply(t, toClassify)
      }
    } yield classified
  }
}
