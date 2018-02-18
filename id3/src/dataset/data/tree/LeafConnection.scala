package dataset.data.tree

case class LeafConnection[A: Ordering](arc: A, to: Leaf[A], probability: Double = 1.0)
