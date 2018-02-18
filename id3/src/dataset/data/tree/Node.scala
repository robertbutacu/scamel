package dataset.data.tree

case class Node[A: Ordering, B](attribute: B,
                                nodes: List[NodeConnection[A, B]] = List.empty,
                                leafs: List[LeafConnection[A]] = List.empty)