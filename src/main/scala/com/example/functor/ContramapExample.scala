package com.example.functor

/**
  * Created by dave on 12/11/17.
  */
final case class Box[A](value: A)

trait Printable[A] {
  def format(value: A): String
  def contramap[B](func: B => A): Printable[B] = {
    val self = this
    new Printable[B] {
      def format(value: B): String =
        self.format(func(value))
    }
  }
}

object PrintableInstances{
  implicit def boxPrintable[A](implicit p: Printable[A]) =
    p.contramap[Box[A]](_.value)
  implicit val stringPrintable =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }
  implicit val booleanPrintable =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if(value) "yes" else "no"
    }
}
object Printable{


  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)
}


object ContramapExample extends App{
 import PrintableInstances._
  println(Printable.format("Hello"))
  println(Printable.format(true))
  println(Printable.format(Box("hello world")))
  println(Printable.format(Box(true)))
}
