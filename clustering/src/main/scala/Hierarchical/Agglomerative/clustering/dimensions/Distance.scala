package Hierarchical.Agglomerative.clustering.dimensions


trait Distance {
  def computeDistance[A](A: Point[A], B: Point[A]): A
}

object Distance {
  implicit def euclidianDistance: Distance = new Distance {
    override def computeDistance[A](A: Point[A], B: Point[A]): A = ???
  }
}
