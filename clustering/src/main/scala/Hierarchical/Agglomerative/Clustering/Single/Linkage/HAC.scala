package Hierarchical.Agglomerative.Clustering.Single.Linkage

import Hierarchical.Agglomerative.Clustering.Point

/*
  Use Euclidean Distance, and draw the dendrogram.

  1. Calculate the Euclidian Distance,

  2. Create the Distance Matrix
      d(x, y) = rad( (x.x - y.y)^2 + (x.y - y.y) ^ 2 )

  3. From the Distance Matrix, get minimum Element and form a Dendogram.

  4. Recalculate the Distance.
    To Update the Distance Matrix  => Min[Dist((points chosen at step 3), referencePoint)]
                                      => Min(dist(point1, referencePoint), dist(point2, referencePoint)]

    Reference point - the rest of the points in the set.
 */

object HAC {
  def distance(A: Point, B: Point): Double =
    round(Math.pow(Math.pow(A.X - B.X, 2) + Math.pow(A.Y - B.Y, 2), 0.5))


  private def round(x: Double): Double =
    ( x * 1000).floor / 1000
}
