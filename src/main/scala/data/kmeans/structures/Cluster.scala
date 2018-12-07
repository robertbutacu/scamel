package data.kmeans.structures

import algorithms.kmeans.KMeans.DistancesToCentroids
import scala.language.higherKinds

case class Cluster[P[_], A](centroid: Centroid[P, A], points: List[P[A]])

object Cluster {
  def createCluster[P[_], A](centroid: Centroid[P, A], points: List[P[A]])
                    (implicit centroidCalculator: CentroidCalculator[P], frac: Fractional[A]): Cluster[P, A] =
    Cluster(centroidCalculator.repositionCentroid(centroid, points), points)

  def apply[P[_], A](distancesToCentroids: DistancesToCentroids[P, A]): Cluster[P, A] = {
    require(distancesToCentroids.headOption.nonEmpty)
    Cluster(distancesToCentroids.head.centroid, distancesToCentroids.map(o => o.point))
  }
}
