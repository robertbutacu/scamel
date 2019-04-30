package example.kmeans

import java.util.UUID

import algorithms.kmeans.{CentroidInitializer, KMeans, WithPlotter}
import common.data.BidimensionalPoint
import data.hierarchical.clustering.distance.EuclideanDistance

import scala.util.Random

object KMeansExample extends App {
  val randomPoints = (0 to 30000).map(_ => BidimensionalPoint(UUID.randomUUID().toString,Math.abs(Random.nextDouble() % 10), Math.abs(Random.nextDouble() % 10))).toList
  val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](10, randomPoints)(CentroidInitializer.bidimensionalRandomInitializer)

  println(result)
  WithPlotter("docs/img/", "Kmeanstest").plot(result, 0)
}