package com.example.typeclasses


trait Truthable[A] {
  def truthy(value: A): Boolean
}
object TruthableInstances {
  implicit val stringTruthable = new Truthable[String] {
    def truthy(input: String) = input.length>0
  }
  implicit val intTruthable = new Truthable[Int] {
    def truthy(input: Int) = input!=0
  }

}

object TruthableSyntax {
  implicit class PrintOps[A](value: A) {
    def truthy(implicit p: Truthable[A]): Boolean =
      p.truthy(value)
  }
}

object MyApp extends App{
  import TruthableInstances._
  import TruthableSyntax._
  println("".truthy)
  println("fdsa".truthy)
}

