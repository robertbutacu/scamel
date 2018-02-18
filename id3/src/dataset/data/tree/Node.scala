package dataset.data.tree


case class Node[A: Ordering, B](attribute: B,
                nodes: List[Connection[A, B]] = List.empty,
                leafs: List[(A, Leaf[A])] = List.empty)