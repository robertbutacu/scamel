package kmeans.structures

case class Cluster(centroid: Centroid, points: List[Point])

object Cluster {
  def apply(centroid: Centroid, points: List[Point]): Cluster = {
    new Cluster(repositionCentroid(centroid, points), points)
  }

  def repositionCentroid(centroid: Centroid, points: List[Point]): Centroid = {
    val XAxisSum = points.foldRight(0.0)((p, sum) => p.X + sum)
    val YAxisSum = points.foldRight(0.0)((p, sum) => p.Y + sum)

    Centroid(centroid.name, XAxisSum / points.length, YAxisSum / points.length)
  }
}
