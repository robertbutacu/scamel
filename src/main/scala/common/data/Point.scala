package common.data

import cats.Monoid

trait Point[A] {
  def name: String
}

case class UnidimensionalPoint[A: Numeric](name: String, X: A)             extends Point[A]
case class BidimensionalPoint[A: Numeric](name: String, X: A, Y: A)        extends Point[A]
case class TridimensionalPoint[A: Numeric](name: String, X: A, Y: A, Z: A) extends Point[A]

object UnidimensionalPoint {
  implicit def uniMonoid[A](implicit N: Numeric[A]): Monoid[UnidimensionalPoint[A]] = new Monoid[UnidimensionalPoint[A]] {
    override def empty: UnidimensionalPoint[A] = UnidimensionalPoint("", N.zero)

    override def combine(x: UnidimensionalPoint[A], y: UnidimensionalPoint[A]): UnidimensionalPoint[A] = {
      val name = if(x.name < y.name) x.name + y.name else y.name + x.name
      UnidimensionalPoint(name, N.plus(x.X, y.X))
    }
  }
}

object BidimensionalPoint {
  implicit def biMonoid[A](implicit N: Numeric[A]): Monoid[BidimensionalPoint[A]] = new Monoid[BidimensionalPoint[A]] {
    override def empty: BidimensionalPoint[A] = BidimensionalPoint("", N.zero, N.zero)

    override def combine(x: BidimensionalPoint[A], y: BidimensionalPoint[A]): BidimensionalPoint[A] = {
      val name = if(x.name < y.name) x.name + y.name else y.name + x.name

      BidimensionalPoint(name, N.plus(x.X, y.X), N.plus(x.Y, y.Y))
    }
  }
}

object TridimensionalPoint {
  implicit def triMonoid[A](implicit N: Numeric[A]): Monoid[TridimensionalPoint[A]] = new Monoid[TridimensionalPoint[A]] {
    override def empty: TridimensionalPoint[A] = TridimensionalPoint("", N.zero, N.zero, N.zero)

    override def combine(x: TridimensionalPoint[A], y: TridimensionalPoint[A]): TridimensionalPoint[A] = {
      val name = if(x.name < y.name) x.name + y.name else y.name + x.name

      TridimensionalPoint(name, N.plus(x.X, y.X), N.plus(x.Y, y.Y), N.plus(x.Z, y.Z))
    }
  }
}