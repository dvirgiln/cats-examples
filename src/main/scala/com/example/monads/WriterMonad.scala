package com.example.monads
import cats.data.Writer
import cats.instances.vector._
import cats.implicits._
import cats.syntax.writer._

/**
  * Created by dave on 25/11/17.
  */
object WriterMonad extends App{
/*  import cats.syntax.applicative._ // `pure` method
  type Logged[A] = Writer[Vector[String], A]
 val writer1 = for {
    a <- 10.pure[Logged]
    _ <- Vector("a", "b", "c").tell
    b <- 32.writer(Vector("x", "y", "z"))
  } yield a + b

  writer1.run
*/
}
