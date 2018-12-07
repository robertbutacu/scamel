package data.id3.tree

import data.id3.Subset

case class LeafConnection[A](arc: A, to: Leaf[A], probability: Double = 1.0)

object LeafConnection {
  def apply[A, B](subset: Subset[A, B]): LeafConnection[A] = LeafConnection(subset.attribute, Leaf(subset))
}
