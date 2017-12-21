package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.{Cluster, Point}

trait Method {
  def formCluster(clusters: List[Cluster]): (Cluster, Cluster, Double)

  final def distanceBetweenPoints(current: Point, other: Point): Double = {
    Math.sqrt(Math.pow(current.X - other.Y, 2) + Math.pow(current.Y - other.Y, 2))
  }
}
