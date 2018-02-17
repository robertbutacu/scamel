package kmeans.structures

case class DistanceToCentroid(point: Point, centroid: Centroid, distance: Double)

object DistanceToCentroid {
  def apply(p: Point, c: Centroid): DistanceToCentroid = {
    def computeDistance = Math.sqrt(Math.pow(p.X - c.X, 2) + Math.pow(p.Y - c.Y, 2))

    DistanceToCentroid(p, c, computeDistance)
  }
}
