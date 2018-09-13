package com.example.typeclasses

/**
  * Created by dave on 12/11/17.
  */
trait Printable[A] {
  def format(value: A): String
}
object PrintableInstances {
  implicit val stringPrintable = new Printable[String] {
    def format(input: String) = input
  }
  implicit val intPrintable = new Printable[Int] {
    def format(input: Int) = input.toString
  }
  implicit val catPrintable = new Printable[Cat] {
    def format(cat: Cat) = {
      val name = Printable.format(cat.name)
      val age = Printable.format(cat.age)
      val color = Printable.format(cat.color)
      s"$name is a $age year-old $color cat."
    }
  }
}

object Printable {
  def format[A](input: A)(implicit p: Printable[A]): String =
    p.format(input)
  def print[A](input: A)(implicit p: Printable[A]): Unit =
    println(format(input))
}



final case class Cat(name: String, age: Int, color: String)


//EXTENSION USING IMPLICIT CLASSES
object PrintableSyntax {
  implicit class PrintOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)
    def print(implicit p: Printable[A]): Unit =
      println(p.format(value))
  }
}

object PrintableApp extends App{
  import PrintableInstances._
  val cat = Cat("Garfield", 35, "ginger and black")
  Printable.print(cat)

  //Extension Using implicit classes
  import PrintableSyntax._
  cat.print
  cat.format
  //AMAZING!!!

  //Now using Cats show to do the same
  import cats.Show
  import cats.instances.int._
  import cats.instances.string._
  import cats.syntax.show._
  implicit val catShow = Show.show[Cat] { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }
  println(Cat("Garfield", 35, "ginger and black").show)
}