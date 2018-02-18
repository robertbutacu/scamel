package dataset.data.tree

case class Node(attribute: String,
                nodes: List[Node] = List.empty,
                leafs: List[Leaf] = List.empty)