package data.hierarchical.clustering.distance

import common.data.{BidimensionalPoint, TridimensionalPoint, UnidimensionalPoint}

import scala.language.higherKinds

trait Distance[A, P[_], D] {
  def computeDistance(from: P[A], to: P[A]): A
}

object Distance {

  /**
    Euclidean distance is represented by the formula:
      Sqrt of sum by i ( Math.pow(Ai - Bi, 2))
   */

  implicit def euclidean1D[D](implicit F: Fractional[D]): Distance[D, UnidimensionalPoint, EuclideanDistance.type] = new Distance[D, UnidimensionalPoint, EuclideanDistance.type] {
    override def computeDistance(from: UnidimensionalPoint[D], to: UnidimensionalPoint[D]): D = {
      val diff = F.minus(from.X, to.X)
      F.times(diff, diff)
    }
  }

  implicit def euclidean2D[D](implicit F: Fractional[D]): Distance[D, BidimensionalPoint, EuclideanDistance.type] = new Distance[D, BidimensionalPoint, EuclideanDistance.type] {
    override def computeDistance(from: BidimensionalPoint[D], to: BidimensionalPoint[D]): D = {
      val Xdiff = F.minus(from.X, to.X)
      val Ydiff = F.minus(from.Y, to.Y)

      val xTimes = F.times(Xdiff, Xdiff)
      val yTimes = F.times(Ydiff, Ydiff)

      F.plus(xTimes, yTimes)
    }
  }

  implicit def euclidean3D[D](implicit F: Fractional[D]): Distance[D, TridimensionalPoint, EuclideanDistance.type] = new Distance[D, TridimensionalPoint, EuclideanDistance.type] {
    override def computeDistance(from: TridimensionalPoint[D], to: TridimensionalPoint[D]): D = {
      val Xdiff = F.minus(from.X, to.X)
      val Ydiff = F.minus(from.Y, to.Y)
      val Zdiff = F.minus(from.Z, to.Z)

      val xTimes = F.times(Xdiff, Xdiff)
      val yTimes = F.times(Ydiff, Ydiff)
      val zTimes = F.times(Zdiff, Zdiff)

      F.plus(zTimes, F.plus(xTimes, yTimes))
    }
  }

  /**
    Manhattan distance is given by the formula:
    Sum by i in Abs(Ai - Bi)
    */

  implicit def manhattan1D[D](implicit F: Fractional[D]): Distance[D, UnidimensionalPoint, ManhattanDistance.type] = new Distance[D, UnidimensionalPoint, ManhattanDistance.type] {
    override def computeDistance(from: UnidimensionalPoint[D], to: UnidimensionalPoint[D]): D = {
      F.abs(F.minus(from.X, to.X))
    }
  }

  implicit def manhattan2D[D](implicit F: Fractional[D]): Distance[D, BidimensionalPoint, ManhattanDistance.type] = new Distance[D, BidimensionalPoint, ManhattanDistance.type] {
    override def computeDistance(from: BidimensionalPoint[D], to: BidimensionalPoint[D]): D = {
      val xSum = F.abs(F.minus(from.X, to.X))
      val ySum = F.abs(F.minus(from.Y, to.Y))

      F.plus(xSum, ySum)
    }
  }

  implicit def manhattan3D[D](implicit F: Fractional[D]): Distance[D, TridimensionalPoint, ManhattanDistance.type] = new Distance[D, TridimensionalPoint, ManhattanDistance.type] {
    override def computeDistance(from: TridimensionalPoint[D], to: TridimensionalPoint[D]): D = {
      val xSum = F.abs(F.minus(from.X, to.X))
      val ySum = F.abs(F.minus(from.Y, to.Y))
      val zSum = F.abs(F.minus(from.Z, to.Z))

      F.plus(zSum, F.plus(xSum, ySum))
    }
  }

  /**
    Chebyshev distance is given by the formula:
      Max from | Ai - Bi |
    */

  implicit def chebyshev1D[D](implicit F: Fractional[D]): Distance[D, UnidimensionalPoint, ChebyshevDistance.type] = new Distance[D, UnidimensionalPoint, ChebyshevDistance.type] {
    override def computeDistance(from: UnidimensionalPoint[D], to: UnidimensionalPoint[D]): D = {
      F.abs(F.minus(from.X, to.X))
    }
  }

  implicit def chebyshev2D[D](implicit F: Fractional[D]): Distance[D, BidimensionalPoint, ChebyshevDistance.type] = new Distance[D, BidimensionalPoint, ChebyshevDistance.type] {
    override def computeDistance(from: BidimensionalPoint[D], to: BidimensionalPoint[D]): D = {
      val xDiff = F.abs(F.minus(from.X, to.X))
      val yDiff = F.abs(F.minus(from.Y, to.Y))

      F.max(xDiff, yDiff)
    }
  }

  implicit def chebyshev3D[D](implicit F: Fractional[D]): Distance[D, TridimensionalPoint, ChebyshevDistance.type] = new Distance[D, TridimensionalPoint, ChebyshevDistance.type] {
    override def computeDistance(from: TridimensionalPoint[D], to: TridimensionalPoint[D]): D = {
      val xDiff = F.abs(F.minus(from.X, to.X))
      val yDiff = F.abs(F.minus(from.Y, to.Y))
      val zDiff = F.abs(F.minus(from.Z, to.Z))

      F.max(zDiff, F.max(xDiff, yDiff))
    }
  }
}