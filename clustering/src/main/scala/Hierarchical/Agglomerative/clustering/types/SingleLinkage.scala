package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.distances.Distance

case object SingleLinkage extends Method {
  override def formCluster(clusters: List[Cluster])(implicit distance: Distance): NewCluster = {
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
    } yield NewCluster(currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_.distanceBetween)
  }
}
