package dataset.data.tree


//TODO refactor so that the nodes do not represent data too, only attributes
case class Node(attribute: String,
                nodes: List[Node] = List.empty,
                leafs: List[Leaf] = List.empty)

object Node {

}