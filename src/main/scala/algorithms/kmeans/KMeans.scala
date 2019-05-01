package algorithms.kmeans

import data.hierarchical.clustering.distance.Distance
import data.kmeans.structures.{Centroid, CentroidCalculator, Cluster, DistanceToCentroid}

import scala.annotation.tailrec
import scala.language.higherKinds

object KMeans {
  type DistancesToCentroids[P[_], A] = List[DistanceToCentroid[P, A]]
  case class Coordinate[A](X: A, Y: A)

  case class Result[P[_], A](previousIterations: List[List[Cluster[P, A]]], result: List[Cluster[P, A]])

  def findClusters[P[_], A, DT](numberOfClusters: Int,
                            points              : List[P[A]])(initialization: CentroidInitializer[P])
                           (implicit distance   : Distance[A, P, DT],
                            frac                : Fractional[A],
                            centroidCalculator  : CentroidCalculator[P]): Result[P, A] = {
    require(numberOfClusters > 0 && points.nonEmpty)

    //TODO special case when a centroid is passed around between 2 values
    @tailrec
    def go(currClusters          : List[Cluster[P, A]],
           previousStateCentroids: List[Centroid[P, A]],
           previousIterations    : List[List[Cluster[P, A]]]): Result[P, A] = {
      val currentCentroids = currClusters map (_.centroid)

      val updatedClusters = createClusters(points, currentCentroids).map(c => c.copy(centroid = centroidCalculator.repositionCentroid(c.centroid, c.points)))

      val updatedCentroids = updatedClusters map (_.centroid)

      //the clusters stay the same, so check that each cluster has the same points still assigned to it
      if (currentCentroids == updatedCentroids) Result(previousIterations, currClusters)
      else                                      go(updatedClusters, updatedCentroids, previousIterations :+ updatedClusters)
    }

    val initialCentroids = initialization.initialize(points, numberOfClusters)
    val initialClusters = createClusters(points, initialCentroids)

    go(initialClusters, List.empty, List(initialClusters))
  }

  private def createClusters[P[_], A, DP](points: List[P[A]],
                                      centroids: List[Centroid[P, A]])
                                     (implicit distance: Distance[A, P, DP],
                                      frac: Fractional[A]): List[Cluster[P, A]] = {
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
    val newClusters = splitIntoClusters(minDistances)

    newClusters.map { d: DistancesToCentroids[P, A] => Cluster(d) }
  }


  private def splitIntoClusters[P[_], A](input: DistancesToCentroids[P, A]): List[DistancesToCentroids[P, A]] = {
    val result = (input groupBy (_.centroid)).values.toList

    result
  }

  private def splitPoints[P[_], A](input: DistancesToCentroids[P, A]): List[DistancesToCentroids[P, A]] = {
    val result = (input groupBy (_.point)).values.toList

    result
  }
}
