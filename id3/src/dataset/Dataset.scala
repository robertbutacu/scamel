package dataset

case class Dataset(attribute: String, data: List[String])

trait Tree

case class Leaf(attribute: String) extends Tree

case class Node(attribute: String,
                nodes: List[Node] = List.empty,
                leafs: List[Leaf] = List.empty) extends Tree

