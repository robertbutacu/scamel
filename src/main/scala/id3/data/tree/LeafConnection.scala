package id3.data.tree

import id3.data.Subset

case class LeafConnection[A: Ordering](arc: A, to: Leaf[A], probability: Double = 1.0)

object LeafConnection {
  def apply[A: Ordering, B](subset: Subset[A, B]): LeafConnection[A] =
    LeafConnection(subset.attribute, Leaf(subset))
}
