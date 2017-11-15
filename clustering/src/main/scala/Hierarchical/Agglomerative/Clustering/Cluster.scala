package Hierarchical.Agglomerative.Clustering

case class Node(name: String, connection: Connection, coordinates: Point, creationIndex: Int)

case class Connection(first: Either[Point, Node], second: Either[Point, Node])
