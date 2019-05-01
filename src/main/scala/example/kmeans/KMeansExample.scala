package example.kmeans

import java.util.UUID

import algorithms.kmeans.{CentroidInitializer, KMeans, WithPlotter}
import common.data.BidimensionalPoint
import data.hierarchical.clustering.distance.EuclideanDistance

import scala.util.Random

object KMeansExample extends App {
  def randomPoints() = {
    val randomPoints = (0 to 10000).map(_ => BidimensionalPoint(UUID.randomUUID().toString,Math.abs(Random.nextDouble()), Math.abs(Random.nextDouble()))).toList
    val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](5, randomPoints)(CentroidInitializer.bidimensionalRandomInitializer)

    WithPlotter("docs/img/", "Kmeanstest").plot(result, 0)
  }

  def groupedPoints() = {
    val group1 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 1.0, Math.abs(Random.nextDouble()) + 1.0)).toList
    val group2 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 3.0, Math.abs(Random.nextDouble()) + 3.0)).toList
    val group3 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 5.0, Math.abs(Random.nextDouble()) + 5.0)).toList

    val points = group1 ::: group2 ::: group3

    val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](3, points)(CentroidInitializer.bidimensionalRandomInitializer)

    WithPlotter("docs/img/", "GroupedPoints").plot(result, 0)
  }

  def example3() = {
    val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](3,
      List(
        BidimensionalPoint("A", 2.0, 10.0),
        BidimensionalPoint("B", 2.0, 5.0),
        BidimensionalPoint("C", 8.0, 4.0),
        BidimensionalPoint("D", 5.0, 8.0),
        BidimensionalPoint("E", 7.0, 5.0),
        BidimensionalPoint("F", 6.0, 4.0),
        BidimensionalPoint("G", 1.0, 2.0),
        BidimensionalPoint("H", 4.0, 9.0)
      ))(CentroidInitializer.bidimensionalRandomInitializer)

    WithPlotter("docs/img/", "class").plot(result, 0)
  }

  groupedPoints()
  //randomPoints()
  //example3()
}