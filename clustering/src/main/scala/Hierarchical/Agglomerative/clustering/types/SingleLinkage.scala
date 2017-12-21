package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.{Cluster, Point}

case object SingleLinkage extends Method {
  override def formCluster(clusters: List[Cluster]): (Cluster, Cluster, Double) = {
    def shortestDistance(current: Cluster, other: Cluster): Double = {

      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint <- other.points
      } yield distanceBetweenPoints(currentPoint, otherPoint)

      distancesBetweenAllPoints.min
    }

    val shortestDistancesBetweenClusters = for {
      currentCluster <- clusters
      otherCluster <- clusters.filterNot(_ == currentCluster)
    } yield (currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_._3)
  }
}
