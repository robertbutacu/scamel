import Hierarchical.Agglomerative.clustering.dimensions.{ChebyshevDistance, EuclideanDistance}
import Hierarchical.Agglomerative.{Cluster, ClusteringAlgorithm}
import Hierarchical.Agglomerative.clustering.dimensions.points.BidimensionalPoint
import Hierarchical.Agglomerative.clustering.types.{AverageLinkage, CompleteLinkage, SingleLinkage}

object Main extends App {

  /*val points = List(
    Cluster(List(Point("P1", 0.4, 0.53))),
    Cluster(List(Point("P2", 0.22, 0.38))),
    Cluster(List(Point("P3", 0.35, 0.32))),
    Cluster(List(Point("P4", 0.26, 0.19))),
    Cluster(List(Point("P5", 0.08, 0.41))),
    Cluster(List(Point("P6", 0.45, 0.30)))
  )*/
  val points = List(
    Cluster(List(BidimensionalPoint("P1", 0.4, 0.53))),
    Cluster(List(BidimensionalPoint("P2", 0.22, 0.38))),
    Cluster(List(BidimensionalPoint("P3", 0.35, 0.32))),
    Cluster(List(BidimensionalPoint("P4", 0.26, 0.19))),
    Cluster(List(BidimensionalPoint("P5", 0.08, 0.41))),
    Cluster(List(BidimensionalPoint("P6", 0.45, 0.30)))
  )

  println("Average linkage result")
  ClusteringAlgorithm.clusterize(points, ChebyshevDistance, AverageLinkage)
    .prettyPrinter()

  ClusteringAlgorithm.clusterize(points, EuclideanDistance, AverageLinkage)
    .prettyPrinter()

  println("Single linkage result")
  ClusteringAlgorithm.clusterize(points, EuclideanDistance, SingleLinkage)
    .prettyPrinter()
  println()
  ClusteringAlgorithm.clusterize(points, EuclideanDistance, SingleLinkage)
    .prettyPrinter()

  println("Complete linkage result")
  ClusteringAlgorithm.clusterize(points, EuclideanDistance, CompleteLinkage)
    .prettyPrinter()
  println()
  ClusteringAlgorithm.clusterize(points, EuclideanDistance, CompleteLinkage)
    .prettyPrinter()

  //println(Calculator.computeDistance(BidimensionalPoint("A", 4.0, 5.0), BidimensionalPoint("B", 5.0, 5.0), EuclideanDistance))
}
