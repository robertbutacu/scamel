package id3.data.tree

import id3.data.Subset

case class Leaf[A](attribute: A) extends Tree

object Leaf {
  def apply[A, B](subset: Subset[A, B]) = {
    require(subset.conclusion.data.headOption.nonEmpty)
    new Leaf(subset.conclusion.data.head)
  }
}

