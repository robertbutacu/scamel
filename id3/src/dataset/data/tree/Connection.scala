package dataset.data.tree

case class Connection[A: Ordering, B](arc: A, to: Node[A, B], probability: Double = 1.0)
