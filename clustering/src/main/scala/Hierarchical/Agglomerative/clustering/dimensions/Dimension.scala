package Hierarchical.Agglomerative.clustering.dimensions


trait Dimension[A, P[_]] {
  def computeDistance(from: P[A], to: P[A])(implicit distance: Distance): A
}

object Dimension {
  implicit def unidimensionalImplicit[A: Numeric]: Dimension[A, UnidimensionalPoint] =
    new Dimension[A, UnidimensionalPoint] {
      override def computeDistance(from: UnidimensionalPoint[A],
                                   to: UnidimensionalPoint[A])(implicit distance: Distance): A = {
        //distance.computeDistance(from, to)
        //for now, just the euclidean distance
        val num = implicitly[Numeric[A]]

        num.plus(from.X, to.X)
      }
    }
}