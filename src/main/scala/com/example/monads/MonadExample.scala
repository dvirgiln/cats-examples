package com.example.monads

/**
  * Created by dave on 13/11/17.
  */
object MonadExample extends App{
  import cats.syntax.applicative._
  import cats.instances.option._
  import cats.instances.list._
  1.pure[Option]
  // res4: Option[Int] = Some(1)
  1.pure[List]
  // res5: List[In


  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._
  def sumSquare[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] =
    a.flatMap(x => b.map(y => x*x + y*y))
  import cats.instances.option._
  import cats.instances.list._
  println(sumSquare(Option(3), Option(4)))
  // res8: Option[Int] = Some(25)

  println(sumSquare(List(1, 2, 3), List(4, 5)))
}
