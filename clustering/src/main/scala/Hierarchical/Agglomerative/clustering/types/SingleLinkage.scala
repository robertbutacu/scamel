package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.Cluster
import Hierarchical.Agglomerative.clustering.dimensions.clusters.ClusterCentroid
import Hierarchical.Agglomerative.clustering.dimensions.points.Point
import Hierarchical.Agglomerative.clustering.dimensions.{Distance, DistanceType}

case object SingleLinkage extends Method {
  override def formCluster[A: Numeric, P[_] <: Point[_], D <: DistanceType](clusters: List[Cluster[A, P]], distanceType: D)
                                                        (implicit distance: Distance[A, P, D],
                                                         centroidCalculator: ClusterCentroid[A, P]): NewCluster[A, P] = {
    def shortestDistance(current: Cluster[A, P], other: Cluster[A, P]): A = {
      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint <- other.points
      } yield distance.computeDistance(currentPoint, otherPoint, distanceType)

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
