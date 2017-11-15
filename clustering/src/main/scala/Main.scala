import Hierarchical.Agglomerative.Clustering.Point
import Hierarchical.Agglomerative.Clustering.Single.Linkage.HAC

object Main extends App {
  println(HAC(
    List(
      Point("P1", 0.40, 0.53),
      Point("P2", 0.22, 0.38),
      Point("P3", 0.35, 0.32),
      Point("P4", 0.26, 0.19),
      Point("P5", 0.08, 0.41),
      Point("P6", 0.45, 0.30)
    )
  ))
}
