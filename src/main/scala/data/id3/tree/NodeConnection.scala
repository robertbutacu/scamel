package data.id3.tree

case class NodeConnection[A, B](arc: A, to: Node[A, B], probability: Double = 1.0)
