package com.example.functor

/**
  * Created by dave on 13/11/17.
  */


object ContravariantExample extends App{
  import cats._

  import cats.implicits._
  val div2: Int => Double = _ / 2.0
  val add1: Int => Int = _ + 1

  //Not working, doesnt understand why
  //div2.contramap(add1)(2)


  case class Money(amount: Int)
  case class Salary(size: Money)

  implicit val showMoney: Show[Money] = Show.show(m => s"$$${m.amount}")
  implicit val showSalary: Show[Salary] = showMoney.contramap(_.size)


  Salary(Money(1000)).show
  // res3: String = $1000

  Ordering.Int.compare(2, 1)
  // res4: Int = 1

  Ordering.Int.compare(1, 2)

  implicit val moneyOrdering: Ordering[Money] = Ordering.by(_.amount)

  //Required this line to add the comparation of Money with <
  import scala.math.Ordered._
  Money(100) < Money(200)



  implicit val symbolSemigroup: Semigroup[Symbol] =
  Semigroup[String].imap(Symbol.apply)(_.name)

  val as='a |+| 'few |+| 'words
  println(as)




  //Subtyping
  class A
  // defined class A

  class B extends A
  // defined class B

  val b: B = new B
  // b: B = B@566ea1cb

  val a: A = b
  // a: A = B@566ea1cb

  val showA: Show[A] = Show.show(a => "a!")
  // showA: cats.Show[A] = cats.Show$$anon$1@95ecb9d

  val showB1: Show[B] = showA.contramap(b => b: A)
  // showB1: cats.Show[B] = cats.Show$$anon$1@6c9d820e

  val showB2: Show[B] = showA.contramap(identity[A])
  // showB2: cats.Show[B] = cats.Show$$anon$1@6f898b93

  val showB3: Show[B] = Contravariant[Show].narrow[A, B](showA)

}
