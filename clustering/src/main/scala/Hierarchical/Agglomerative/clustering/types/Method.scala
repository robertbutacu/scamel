package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.{Cluster, Point}
import Hierarchical.Agglomerative.clustering.distances.Distance

trait Method {

  def formCluster(clusters: List[Cluster])(implicit distance: Distance): NewCluster

  final def distanceBetweenPoints(current: Point, other: Point)(implicit distance: Distance): Double = {
    distance.computeDistance(current, other)
  }
}
