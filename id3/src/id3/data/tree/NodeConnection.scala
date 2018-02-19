package id3.data.tree

case class NodeConnection[A: Ordering, B](arc: A, to: Node[A, B], probability: Double = 1.0)
