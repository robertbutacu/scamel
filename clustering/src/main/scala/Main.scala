import Hierarchical.Agglomerative.Clustering.Point
import Hierarchical.Agglomerative.Clustering.Single.Linkage.HAC

object Main extends App {
  println(HAC.distance(Point("A", 0.4, 0.53), Point("B", 0.22, 0.38)))
}
