package Hierarchical.Agglomerative.Clustering

case class Node(name: String, connection: List[Either[Node, Point]], creationIndex: Int)

