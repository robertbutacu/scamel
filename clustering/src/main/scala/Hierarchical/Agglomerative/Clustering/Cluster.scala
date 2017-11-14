package Hierarchical.Agglomerative.Clustering

case class Node(connection: Connection, creationIndex: Int)

case class Connection(first: Either[Point, Node], second: Either[Point, Node])
