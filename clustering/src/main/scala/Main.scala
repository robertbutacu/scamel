import Hierarchical.Agglomerative.Clustering.Point
import Hierarchical.Agglomerative.Clustering.Single.Linkage.HAC

object Main extends App {
  println(HAC(
    List(
      Left(Point("P1", 0.40, 0.53)),
      Left(Point("P2", 0.22, 0.38)),
      Left(Point("P3", 0.35, 0.32)),
      Left(Point("P4", 0.26, 0.19)),
      Left(Point("P5", 0.08, 0.41)),
      Left(Point("P6", 0.45, 0.30))
    )
  ))
}
