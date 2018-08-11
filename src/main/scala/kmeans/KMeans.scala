package kmeans

import kmeans.structures.{Centroid, Cluster, DistanceToCentroid, Point}

import scala.annotation.tailrec
import scala.util.Random

object KMeans {
  //TODO extend to N dimensions
  type DistancesToCentroids = List[DistanceToCentroid]
  case class Coordinate(X: Int, Y: Int)

  def findClusters(numberOfClusters: Int, points: List[Point])
                  (initialization: MethodInitialization = RandomInitialization): List[Cluster] = {
    require(numberOfClusters > 0 && points.nonEmpty)

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

    val initialCentroids = initialization.initialize(numberOfClusters, points)
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
}
