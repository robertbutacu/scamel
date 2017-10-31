package dataset

case class Dataset(attribute: String, data: List[String])

case class Leaf(attribute: String)

case class Node(attribute: String,
                nodes: List[Node] = List.empty,
                leafs: List[Leaf] = List.empty)

