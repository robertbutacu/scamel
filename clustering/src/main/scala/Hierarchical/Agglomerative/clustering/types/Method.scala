package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster

trait Method {
  def formCluster(clusters: List[Cluster]): (Cluster, Cluster, Double)
}
