import Hierarchical.Agglomerative.clustering.dimensions._
import Hierarchical.Agglomerative.{Cluster, Point}

object Main extends App {

  val points = List(
    Cluster(List(Point("P1", 0.4, 0.53))),
    Cluster(List(Point("P2", 0.22, 0.38))),
    Cluster(List(Point("P3", 0.35, 0.32))),
    Cluster(List(Point("P4", 0.26, 0.19))),
    Cluster(List(Point("P5", 0.08, 0.41))),
    Cluster(List(Point("P6", 0.45, 0.30)))
  )

  /*println("Average linkage result")
  ClusteringAlgorithm.clusterize(points, AverageLinkage)(DistanceImplicits.euclideanDistance)
    .prettyPrinter()

  ClusteringAlgorithm.clusterize(points, AverageLinkage)(DistanceImplicits.manhattanDistance)
    .prettyPrinter()

  println("Single linkage result")
  ClusteringAlgorithm.clusterize(points, SingleLinkage)(DistanceImplicits.euclideanDistance)
    .prettyPrinter()
  println()
  ClusteringAlgorithm.clusterize(points, SingleLinkage)(DistanceImplicits.manhattanDistance)
    .prettyPrinter()

  println("Complete linkage result")
  ClusteringAlgorithm.clusterize(points, CompleteLinkage)(DistanceImplicits.euclideanDistance)
    .prettyPrinter()
  println()
  ClusteringAlgorithm.clusterize(points, CompleteLinkage)(DistanceImplicits.manhattanDistance)
    .prettyPrinter()*/

  println(Calculator.computeDistance(BidimensionalPoint("A", 4.0, 5.0), BidimensionalPoint("B", 5.0, 5.0), EuclideanDistance))
}
