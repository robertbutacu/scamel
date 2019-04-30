package data.kmeans.structures

import scala.language.higherKinds

case class Centroid[P[_], A](name: String, point: P[A])