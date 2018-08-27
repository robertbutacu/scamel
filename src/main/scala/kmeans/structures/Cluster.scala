package kmeans.structures

import kmeans.KMeans.DistancesToCentroids

case class Cluster(centroid: Centroid, points: List[Point])

object Cluster {
  def apply(centroid: Centroid, points: List[Point]): Cluster =
    new Cluster(repositionCentroid(centroid, points), points)

  def apply(distancesToCentroids: DistancesToCentroids): Cluster = {
    require(distancesToCentroids.headOption.nonEmpty)
    Cluster(distancesToCentroids.head.centroid, distancesToCentroids.map(o => o.point))
  }

  def repositionCentroid(centroid: Centroid, points: List[Point]): Centroid = {
    val centroidCoordinates = points.reduce((p, c) => Point(centroid.name, p.X + c.X, p.Y + c.Y))

    Centroid(centroid.name, centroidCoordinates.X / points.length, centroidCoordinates.Y / points.length)
  }
}
