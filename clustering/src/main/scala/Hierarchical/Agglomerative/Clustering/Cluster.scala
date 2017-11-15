package Hierarchical.Agglomerative.Clustering

case class Cluster(name: String,
                   points: List[Point],
                   childrenClusters: Option[(Cluster, Cluster)] = None,
                   creationIndex: Int = 0)
