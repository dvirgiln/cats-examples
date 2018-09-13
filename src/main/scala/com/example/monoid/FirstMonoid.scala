package com.example.monoid

import cats.{Eq, Monoid}

/**
  * Created by dave on 25/11/17.
  */
object FirstMonoid extends App{

  import cats.implicits._
  case class Last[A: Eq](val unwrap: Option[A])
  case class First[A: Eq](val unwrap: Option[A])
  object Last {
    implicit def lastMonoid[A: Eq]: Monoid[Last[A]] = new Monoid[Last[A]] {
      def combine(a1: Last[A], a2: Last[A]): Last[A] =
        Last((a1.unwrap, a2.unwrap) match {
          case (_, Some(y)) => Some(y)
          case (x, None)    => x
        })
      def empty: Last[A] = Last(None: Option[A])
    }
    implicit def lastEq[A: Eq]: Eq[Last[A]] = new Eq[Last[A]] {
      def eqv(a1: Last[A], a2: Last[A]): Boolean =
        Eq[Option[A]].eqv(a1.unwrap, a2.unwrap)
    }
  }

  object First {
    implicit def firstMonoid[A: Eq]: Monoid[First[A]] = new Monoid[First[A]] {
      def combine(a1: First[A], a2: First[A]): First[A] =
        First((a1.unwrap, a2.unwrap) match {
          case (Some(y), _) => Some(y)
          case (None, x)    => x
        })
      def empty: First[A] = First(None: Option[A])
    }
    implicit def lastEq[A: Eq]: Eq[Last[A]] = new Eq[Last[A]] {
      def eqv(a1: Last[A], a2: Last[A]): Boolean =
        Eq[Option[A]].eqv(a1.unwrap, a2.unwrap)
    }
  }

  import Last._
  import First._
  println(Last('a'.some) |+| Last('b'.some))

  println(First('a'.some) |+| First('b'.some))
}
