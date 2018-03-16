package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster

case class NewCluster(first: Cluster, second: Cluster, distanceBetween: Double)

object NewCluster {
  def createCluster(newCluster: NewCluster, creationIndex: Int): Cluster =
    Cluster(newCluster.first.points ++ newCluster.second.points,
      creationIndex,
      Some(newCluster.first),
      Some(newCluster.second))

}