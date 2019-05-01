package data.hierarchical.clustering.types

import data.hierarchical.clustering.clusters.{Cluster, ClusterCentroid, NewCluster}
import data.hierarchical.clustering.distance.Distance
import scala.language.higherKinds

/*
  Difference between SingleLinkage and CompleteLinkage is the following:
    1. for SingleLinkage it matters the shortest distance between the closest 2 points of 2 given clusters
    2. for CompleteLinkage, it matters the shortest distance between the 2 points that are the farthest away from
      each other of 2 given clusters
 */

case object CompleteLinkage extends Method {
  override def formCluster[A, P[_], D](clusters          : List[Cluster[A, P]])
                                      (implicit ord      : Ordering[A],
                                       distance          : Distance[A, P, D],
                                       centroidCalculator: ClusterCentroid[A, P]): NewCluster[A, P] = {
    def shortestDistance(current: Cluster[A, P], other: Cluster[A, P]): A = {

      val distancesBetweenAllPoints = for {
        currentPoint <- current.points
        otherPoint   <- other.points
      } yield distance.computeDistance(currentPoint, otherPoint)

      distancesBetweenAllPoints.max
    }

    val shortestDistancesBetweenClusters = for {
      currentCluster <- clusters
      otherCluster   <- clusters.filterNot(_ == currentCluster)
    } yield NewCluster(currentCluster, otherCluster, shortestDistance(currentCluster, otherCluster))

    shortestDistancesBetweenClusters.minBy(_.distanceBetween)
  }
}
