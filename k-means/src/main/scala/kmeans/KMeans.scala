package kmeans

import kmeans.structures.{Centroid, Cluster, DistanceToCluster, Point}

import scala.annotation.tailrec
import scala.util.Random

object KMeans {

  def findClusters(numberOfClusters: Int, points: List[Point]): List[Cluster] = {
    require(numberOfClusters > 0 && points.nonEmpty)

    def getMin: (Int, Int) = (points.minBy(_.X).X.toInt, points.minBy(_.Y).Y.toInt)

    def getMax: (Int, Int) = (points.maxBy(_.X).X.toInt, points.maxBy(_.Y).Y.toInt)

    @tailrec
    def go(currClusters: List[Cluster]): List[Cluster] = {
      //TODO kinda bad logic
      // maybe the repositioning should be done AFTER the clusters have been updated
      val currentCentroids = currClusters.map(_.centroid)

      val updatedCentroids = currClusters.map(c => c.repositionCentroid())

      if (currentCentroids == updatedCentroids)
        currClusters
      else {
        val updatedClusters = createClusters(points, updatedCentroids)
        go(updatedClusters)
      }
    }

    go(createClusters(points, instantiateCentroids(numberOfClusters, getMin, getMax)))
  }

  private def createClusters(points: List[Point], centroids: List[Centroid]): List[Cluster] = {
    def distance(point: Point, centroid: Centroid): DistanceToCluster =
      DistanceToCluster(point, centroid)


    val distancesToCentroids = for {
      point <- points
      distance <- centroids.map(c => distance(point, c))
    } yield distance

    val minDistances = splitPoints(distancesToCentroids).map(e => e.minBy(_.distance))

    val sortedDistances = minDistances.sortBy(c => c.centroid.name)

    //TODO some centroids are left behind
    splitIntoClusters(sortedDistances).map(c => Cluster(c.head.centroid, c.map(o => o.point)))
  }

  private def splitIntoClusters(input: List[DistanceToCluster]): List[List[DistanceToCluster]] =
    (input groupBy (_.centroid)).values.toList

  private def splitPoints(input: List[DistanceToCluster]): List[List[DistanceToCluster]] =
    (input groupBy (_.point.name)).values.toList

  private def instantiateCentroids(number: Int, min: (Int, Int), max: (Int, Int)): List[Centroid] = {
    (1 to number)
      .map { p =>
        Centroid("Centroid " + p,
          Random.nextInt(max._1 - min._1) + min._1,
          Random.nextInt(max._2 - min._2) + min._2)
      }.toList
  }
}
