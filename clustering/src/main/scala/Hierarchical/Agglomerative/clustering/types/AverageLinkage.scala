package Hierarchical.Agglomerative.clustering.types
import Hierarchical.Agglomerative.Cluster

case object AverageLinkage extends Method {
  override def formCluster(clusters: List[Cluster]): (Cluster, Cluster, Double) = ???
}
