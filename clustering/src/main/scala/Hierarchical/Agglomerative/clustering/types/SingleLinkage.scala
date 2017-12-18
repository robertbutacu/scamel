package Hierarchical.Agglomerative.clustering.types
import Hierarchical.Agglomerative.Cluster

case object SingleLinkage extends Method {
  override def formCluster(clusters: List[Cluster]): Unit = ???
}
