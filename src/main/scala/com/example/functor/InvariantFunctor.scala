package com.example.functor

/**
  * Created by dave on 12/11/17.
  */


trait Codec[A] {
  def encode(value: A): String
  def decode(value: String): Option[A]
  def imap[B](dec: A => B, enc: B => A): Codec[B] = {
    val self = this
    new Codec[B] {
      def encode(value: B): String =
        self.encode(enc(value))
      def decode(value: String): Option[B] =self.decode(value).map(dec)
    }
  }
}

object InvariantFunctorInstances{
  implicit val intCodec =
    new Codec[Int] {
      def encode(value: Int): String =
        value.toString
      def decode(value: String): Option[Int] =
        scala.util.Try(value.toInt).toOption
    }

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap[Box[A]](Box(_), _.value)
}
object InvariantFunctor{
  def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): Option[A] =
    c.decode(value)
}

object InvariantFunctorApp extends App{
  import InvariantFunctorInstances._
  println(InvariantFunctor.encode(Box(123)))
  println(InvariantFunctor.decode[Box[Int]]("123"))
}