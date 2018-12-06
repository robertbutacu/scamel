package kmeans

import Hierarchical.Agglomerative.clustering.dimensions.{Distance, EuclideanDistance}
import kmeans.structures.{Centroid, Cluster, DistanceToCentroid}

import scala.annotation.tailrec

object KMeans {
  type DistancesToCentroids[P[_], A] = List[DistanceToCentroid[P, A]]
  case class Coordinate[A](X: A, Y: A)

  def findClusters[P[_], A](numberOfClusters: Int, points: List[P[A]])(initialization: CentroidInitializer[P])
                           (implicit distance: Distance[A, P, EuclideanDistance.type]): List[Cluster[P, A]] = {
    require(numberOfClusters > 0 && points.nonEmpty)

    //TODO special case when a centroid is passed around between 2 values
    @tailrec
    def go(currClusters: List[Cluster[P, A]], previousStateCentroids: List[Centroid[P, A]]): List[Cluster[P, A]] = {
      val currentCentroids = currClusters map (_.centroid)

      val updatedClusters = createClusters(points, currentCentroids)

      val updatedCentroids = updatedClusters map (_.centroid)

      if (currentCentroids == updatedCentroids) currClusters
      else                                      go(updatedClusters, updatedCentroids)
    }

    val initialCentroids = initialization.initialize(points, numberOfClusters)
    val initialClusters = createClusters(points, initialCentroids)

    go(initialClusters, List.empty)
  }

  private def createClusters[P[_], A](points: List[P[A]],
                                      centroids: List[Centroid[P, A]])
                                     (implicit distance: Distance[A, P, EuclideanDistance.type ]): List[Cluster[P, A]] = {
    //compute , for each point, the distance to each centroid
    val distancesToCentroids = for {
      point    <- points
      distance <- centroids.map(c => DistanceToCentroid.createCentroid(point, c))
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


  private def splitIntoClusters[P[_], A](input: DistancesToCentroids[P, A]): List[DistancesToCentroids[P, A]] =
    (input groupBy (_.centroid)).values.toList

  private def splitPoints[P[_], A](input: DistancesToCentroids[P, A]): List[DistancesToCentroids[P, A]] =
    (input groupBy (_.point)).values.toList
}
