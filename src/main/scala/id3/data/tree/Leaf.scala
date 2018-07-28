package id3.data.tree

import id3.data.Subset

case class Leaf[A: Ordering](attribute: A) extends Tree

object Leaf {
  def apply[A: Ordering, B](subset: Subset[A, B]) =
    new Leaf(subset.conclusion.data.head)
}

