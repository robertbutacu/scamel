import Hierarchical.Agglomerative.{Cluster, Point}
import Hierarchical.Agglomerative.ClusteringAlgorithm._
import Hierarchical.Agglomerative.clustering.types.{AverageLinkage, CompleteLinkage, SingleLinkage}

object Main extends App {

  val points = List(
    Cluster(List(Point("P1", 0.4, 0.53))),
    Cluster(List(Point("P2", 0.22, 0.38))),
    Cluster(List(Point("P3", 0.35, 0.32))),
    Cluster(List(Point("P4", 0.26, 0.19))),
    Cluster(List(Point("P5", 0.08, 0.41))),
    Cluster(List(Point("P6", 0.45, 0.30)))
  )

  println("Average linkage result")
  clusterize(points, AverageLinkage)
    .prettyPrinter()


  println("Single linkage result")
  clusterize(points, SingleLinkage)
    .prettyPrinter()

  println("Complete linkage result")
  clusterize(points, CompleteLinkage)
    .prettyPrinter()
}
