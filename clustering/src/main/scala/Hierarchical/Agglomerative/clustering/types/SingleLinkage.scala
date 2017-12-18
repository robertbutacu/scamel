package Hierarchical.Agglomerative.clustering.types

import Hierarchical.Agglomerative.{Cluster, Point}

case object SingleLinkage extends Method {
  override def formCluster(clusters: List[Cluster]): (Cluster, Cluster, Double) = {
    //the formula for the distance between 2 points is sqrt( ( x2 - x1) ^ 2 + (y2 - y1) ^ 2 )
    def shortestDistance(current: Cluster, other: Cluster): Double = {
      def distanceBetweenPoints(current: Point, other: Point): Double = {
        Math.sqrt(Math.pow(current.X - other.Y, 2) + Math.pow(current.Y - other.Y, 2))
      }

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
