package kmeans

import kmeans.structures.{Centroid, Cluster, Point}

import scala.annotation.tailrec
import scala.util.Random

object KMeans {

  def findClusters(numberOfClusters: Int, points: List[Point]): List[Cluster] = {
    require(numberOfClusters > 0 && points.nonEmpty)

    def getMin: (Int, Int) = (points.minBy(_.X).X.toInt, points.minBy(_.Y).Y.toInt)

    def getMax: (Int, Int) = (points.maxBy(_.X).X.toInt, points.maxBy(_.Y).Y.toInt)

    @tailrec
    def go(currClusters: List[Cluster]): List[Cluster] = {
      val currentCentroids = currClusters.map(_.centroid)

      val updatedCentroids = currClusters.map(c => c.repositionCentroid())

      if (currentCentroids == updatedCentroids)
        currClusters
      else {
        val updatedClusters = createClusters(points, updatedCentroids)
        go(updatedClusters)
      }
    }

    go(createClusters(points, instantiateCentroids(numberOfClusters, getMin , getMax)))
  }

  private def createClusters(points: List[Point], centroids: List[Centroid]): List[Cluster] = {
    def distance(point: Point, centroid: Centroid): (Point, Centroid, Double) =
      (point, centroid, Math.sqrt(Math.pow(point.X - centroid.X, 2) + Math.pow(point.Y - centroid.Y, 2)))


    val distancesToCentroids = for {
      point <- points
      distance <- centroids.map(c => distance(point, c))
    } yield distance

    val minDistances = splitPoint(distancesToCentroids).map(e => e.minBy(_._3))

    splitIntoClusters(minDistances.sortBy(c => c._2.name)).map(c => Cluster(c.head._2, c.map(o => o._1)))
  }

  private def splitIntoClusters(input: List[(Point, Centroid, Double)]): List[List[(Point, Centroid, Double)]] = {
    val (packed, next) = input.span(_._2 == input.head._2)

    if (next.isEmpty) List(packed)
    else packed :: splitIntoClusters(next)
  }

  private def splitPoint(input: List[(Point, Centroid, Double)]): List[List[(Point, Centroid, Double)]] = {
    val (packed, next) = input.span(_._1.name == input.head._1.name)

    if (next.isEmpty) List(packed)
    else packed :: splitPoint(next)
  }

  private def instantiateCentroids(number: Int, min: (Int, Int), max: (Int, Int)): List[Centroid] = {
    (1 to number)
      .zipWithIndex
      .map { p =>
        Centroid("Centroid " + p._2,
          Random.nextInt(max._1 - min._1) + min._1,
          Random.nextInt(max._2 - min._2) + min._2)
      }.toList
  }
}
