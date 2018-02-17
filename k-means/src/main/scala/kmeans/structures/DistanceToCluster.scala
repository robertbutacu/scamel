package kmeans.structures

case class DistanceToCluster(point: Point, centroid: Centroid, distance: Double)

object DistanceToCluster {
  def apply(p: Point, c: Centroid): DistanceToCluster = {
    def computeDistance = Math.sqrt(Math.pow(p.X - c.X, 2) + Math.pow(p.Y - c.Y, 2))

    DistanceToCluster(p, c, computeDistance)
  }
}
