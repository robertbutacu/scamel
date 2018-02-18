package dataset.data.tree


//TODO refactor so that the nodes do not represent data too, only attributes
case class Node[A: Ordering, B](attribute: B,
                nodes: List[(A, Node[A, B])] = List.empty,
                leafs: List[(A, Leaf[A])] = List.empty)

object Node {

}