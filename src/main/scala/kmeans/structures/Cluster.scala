package kmeans.structures

import kmeans.KMeans.DistancesToCentroids

case class Cluster[P[_], A](centroid: Centroid[P, A], points: List[P])

object Cluster {
  def apply[P[_], A](centroid: Centroid[P, A], points: List[P[A]])
                    (implicit centroidCalculator: CentroidCalculator[P], frac: Fractional[A]): Cluster[P, A] =
    Cluster(centroidCalculator.repositionCentroid(centroid, points), points)

  def apply[P[_], A](distancesToCentroids: DistancesToCentroids[P, A]): Cluster[P, A] = {
    require(distancesToCentroids.headOption.nonEmpty)
    Cluster(distancesToCentroids.head.centroid, distancesToCentroids.map(o => o.point))
  }
}
