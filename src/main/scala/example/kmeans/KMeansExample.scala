package example.kmeans

import java.util.UUID

import algorithms.kmeans.{CentroidInitializer, KMeans, WithPlotter}
import common.data.BidimensionalPoint
import data.hierarchical.clustering.distance.EuclideanDistance

import scala.util.Random

object KMeansExample extends App {
  val randomPoints = (0 to 20).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Random.nextDouble() % 10, Random.nextDouble() % 10)).toList
  val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](4, randomPoints)(CentroidInitializer.bidimensionalRandomInitializer)

  println(result)
  WithPlotter("docs/img/", "Kmeanstest").plot(result, 0)
}