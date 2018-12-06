package hierarchical.agglomerative.clustering.types

import hierarchical.agglomerative.Cluster
import hierarchical.agglomerative.clustering.dimensions.Distance
import hierarchical.agglomerative.clustering.dimensions.clusters.ClusterCentroid

case object SingleLinkage extends Method {
  override def formCluster[A, P[_], D](clusters: List[Cluster[A, P]], distanceType: D)
                                      (implicit distance: Distance[A, P, D],
                                       centroidCalculator: ClusterCentroid[A, P],
                                       ord: Ordering[A]): NewCluster[A, P] = {
    def shortestDistance(current: Cluster[A, P], other: Cluster[A, P]): A = {
      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint   <- other.points
      } yield distance.computeDistance(currentPoint, otherPoint)

      distancesBetweenAllPoints.minBy(d => d)
    }

    val shortestDistancesBetweenClusters = for {
      currentCluster <- clusters
      otherCluster   <- clusters.filterNot(_ == currentCluster)
    } yield NewCluster(currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_.distanceBetween)
  }
}
