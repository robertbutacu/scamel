package example.kmeans

import java.time.Instant
import java.util.UUID

import algorithms.kmeans.{CentroidInitializer, KMeans, WithPlotter}
import common.data.BidimensionalPoint
import data.hierarchical.clustering.distance.EuclideanDistance

import scala.util.Random

object KMeansExample extends App {
  def randomPoints() = {
    val randomPoints = (0 to 20000).map(_ => BidimensionalPoint(UUID.randomUUID().toString,Math.abs(Random.nextDouble()), Math.abs(Random.nextDouble()))).toList
    val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](15, randomPoints)(CentroidInitializer.bidimensionalRandomInitializer)

    println(s"[${Instant.now}] Finished Kmeans algorithm for randomPoints")
    result.previousIterations.zipWithIndex.foreach(c => WithPlotter("docs/img/randompoints/", "Kmeanstest").plot(c._1, c._2))
  }

  def groupedPoints() = {
    val group1 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 1.0, Math.abs(Random.nextDouble()) + 1.0)).toList
    val group2 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 3.0, Math.abs(Random.nextDouble()) + 3.0)).toList
    val group3 = (0 to 1000).map(_ => BidimensionalPoint(UUID.randomUUID().toString, Math.abs(Random.nextDouble()) + 5.0, Math.abs(Random.nextDouble()) + 5.0)).toList

    val points = group1 ::: group2 ::: group3

    val result = KMeans.findClusters[BidimensionalPoint, Double, EuclideanDistance.type](3, points)(CentroidInitializer.bidimensionalRandomInitializer)

    println(s"[${Instant.now}] Finished Kmeans algorithm for groupedPoints")
    result.previousIterations.zipWithIndex.foreach(c => WithPlotter("docs/img/groupedpoints/", "GroupedPoints").plot(c._1, c._2))
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

    println(s"[${Instant.now}] Finished Kmeans algorithm for example3")
    result.previousIterations.zipWithIndex.foreach(c => WithPlotter("docs/img/example3/", "class").plot(c._1, c._2))
  }

  println(s"[${Instant.now}] Starting groupedPoints")
  //groupedPoints()

  println(s"[${Instant.now}] Starting randomPoints")
  randomPoints()

  println(s"[${Instant.now}] Starting example3")
  //example3()
}