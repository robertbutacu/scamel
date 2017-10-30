package dataset

case class Dataset(attribute: String, data: List[String])

case class Leaf(attribute: String)

case class Node(attribute: String, leafs: List[(String, Leaf)] = List.empty, nodes: List[(String, Node)] = List.empty)
