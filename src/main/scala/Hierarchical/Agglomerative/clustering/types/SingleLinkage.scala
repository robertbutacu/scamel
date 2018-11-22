package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.dimensions.points.Point
import Hierarchical.Agglomerative.clustering.dimensions.{Distance, DistanceType}

case object SingleLinkage extends Method {
  override def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                                      (implicit distance: Distance[A, P, D],
                                       centroidCalculator: ClusterCentroid[A, P],
                                       ord: Ordering[A]): NewCluster[A, P] = {
    def shortestDistance(current: Cluster[A, P], other: Cluster[A, P]): A = {
      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint   <- other.points
      } yield distance.computeDistance(currentPoint, otherPoint, distanceType)

      distancesBetweenAllPoints.minBy(d => d)
    }

    val shortestDistancesBetweenClusters = for {
      currentCluster <- clusters
      otherCluster   <- clusters.filterNot(_ == currentCluster)
    } yield NewCluster(currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_.distanceBetween)
  }
}
