package data.hierarchical.clustering.dimensions

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait Space[A, P[_]] {
  def points: List[P[A]]
}

case class UnidimensionalSpace[A: Numeric](points: List[UnidimensionalPoint[A]]) extends Space[A, UnidimensionalPoint]
case class BidimensionalSpace[A: Numeric](points: List[BidimensionalPoint[A]])   extends Space[A, BidimensionalPoint]
case class TridimensionalSpace[A: Numeric](points: List[TridimensionalPoint[A]]) extends Space[A, TridimensionalPoint]
