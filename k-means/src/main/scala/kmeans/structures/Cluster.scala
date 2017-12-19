package kmeans.structures

case class Cluster(centroid: Centroid, points: List[Point]) {
  def repositionCentroid(): Centroid = {
    val XAxisSum = points.foldRight(0.0)((p, sum) => p.X + sum)
    val YAxisSum = points.foldRight(0.0)((p, sum) => p.Y + sum)

    Centroid(centroid.name, XAxisSum / points.length, YAxisSum / points.length)
  }
}
