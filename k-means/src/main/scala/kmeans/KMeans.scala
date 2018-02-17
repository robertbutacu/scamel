package kmeans

import kmeans.structures.{Centroid, Cluster, DistanceToCentroid, Point}

import scala.annotation.tailrec
import scala.util.Random

object KMeans {
  type DistancesToCentroids = List[DistanceToCentroid]
  type Coordonate = (Int, Int)

  def findClusters(numberOfClusters: Int, points: List[Point]): List[Cluster] = {
    require(numberOfClusters > 0 && points.nonEmpty)

    def getMin: Coordonate = (points.minBy(_.X).X.toInt, points.minBy(_.Y).Y.toInt)

    def getMax: Coordonate = (points.maxBy(_.X).X.toInt, points.maxBy(_.Y).Y.toInt)

    //TODO special case when a centroid is passed around between 2 values
    @tailrec
    def go(currClusters: List[Cluster], previousStateCentroids: List[Centroid]): List[Cluster] = {
      val currentCentroids = currClusters map (_.centroid)

      val updatedClusters = createClusters(points, currentCentroids)

      val updatedCentroids = updatedClusters map (_.centroid)

      if (currentCentroids == updatedCentroids)
        currClusters
      else
        go(updatedClusters, updatedCentroids)
    }

    val initialCentroids = instantiateCentroids(numberOfClusters, getMin, getMax)
    val initialClusters = createClusters(points, initialCentroids)

    go(initialClusters, List.empty)
  }

  private def createClusters(points: List[Point], centroids: List[Centroid]): List[Cluster] = {
    //compute , for each point, the distance to each centroid
    val distancesToCentroids = for {
      point <- points
      distance <- centroids.map(c => DistanceToCentroid(point, c))
    } yield distance

    //now obtain the shortest distance
    val minDistances = splitPoints(distancesToCentroids).map(_.minBy(_.distance))

    //now create clusters as the list is ordered by centroids
    /*
      For example, this:
        DistanceToCentroid(point, Centroid1, X)
        DistanceToCentroid(point, Centroid1, X)
        DistanceToCentroid(point, Centroid2, X)

      will become 2 clusters
     */
    //TODO some centroids are left behind
    splitIntoClusters(minDistances).map(Cluster(_))
  }


  private def splitIntoClusters(input: DistancesToCentroids): List[DistancesToCentroids] =
    (input groupBy (_.centroid)).values.toList

  private def splitPoints(input: DistancesToCentroids): List[DistancesToCentroids] =
    (input groupBy (_.point)).values.toList


  //TODO dont instantiate centroids randomly, instead use KMeans++
  private def instantiateCentroids(number: Int, min: Coordonate, max: Coordonate): List[Centroid] =
    (1 to number)
      .map { p =>
        Centroid("Centroid " + p,
          Random.nextInt(max._1 - min._1) + min._1,
          Random.nextInt(max._2 - min._2) + min._2)
      }.toList
}
