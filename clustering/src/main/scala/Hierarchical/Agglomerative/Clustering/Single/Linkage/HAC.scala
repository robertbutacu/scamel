package Hierarchical.Agglomerative.Clustering.Single.Linkage

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

}
