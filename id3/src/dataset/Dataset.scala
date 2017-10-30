package dataset

case class Dataset(attribute: String, data: List[String])

case class Leaf(attribute: String)

case class Node(attribute: String, leafs: List[Leaf] = List.empty, nodes: List[Node] = List.empty)
