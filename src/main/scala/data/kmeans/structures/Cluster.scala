package data.kmeans.structures

import algorithms.kmeans.KMeans.DistancesToCentroids
import scala.language.higherKinds

case class Cluster[P[_], A](centroid: Centroid[P, A], points: List[P[A]])

object Cluster {
  def apply[P[_], A](distancesToCentroids: DistancesToCentroids[P, A]): Cluster[P, A] = {
    require(distancesToCentroids.headOption.nonEmpty)
    // each point has the same centroid, it doesn't matter which one it is picked
    val cluster = Cluster(distancesToCentroids.head.centroid, distancesToCentroids.map(o => o.point))

    cluster
  }
}
