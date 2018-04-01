package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.Point
import Hierarchical.Agglomerative.clustering.distances.Distance

case object SingleLinkage extends Method {
  override def formCluster[A: Numeric, P[_] <: Point[_]](clusters: List[Cluster[A, P]]): NewCluster[A, P] = {
    def shortestDistance(current: Cluster[A, P], other: Cluster[A, P]): A = {
      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint <- other.points
      } yield distanceBetweenPoints(currentPoint, otherPoint)

      //distancesBetweenAllPoints.min
      implicitly[Numeric[A]].zero
    }

    val shortestDistancesBetweenClusters = for {
      currentCluster <- clusters
      otherCluster <- clusters.filterNot(_ == currentCluster)
    } yield NewCluster(currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_.distanceBetween)
  }
}
