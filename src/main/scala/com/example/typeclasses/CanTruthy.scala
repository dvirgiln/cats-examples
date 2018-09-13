package com.example.typeclasses


trait CanTruthy[A] { self =>
  /** Return true, if `a` is truthy. */
  def truthy(a: A): Boolean
}

// This is the supposed generated code. You don't have to write it!
object CanTruthy {
  def fromTruthy[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    def truthy(a: A): Boolean = f(a)
  }

  def apply[A](implicit instance: CanTruthy[A]): CanTruthy[A] = instance

  trait Ops[A] {
    def typeClassInstance: CanTruthy[A]
    def self: A
    def truthy: Boolean = typeClassInstance.truthy(self)
  }

  trait ToCanTruthyOps {
    implicit def toCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): Ops[A] = new Ops[A] {
      val self = target
      val typeClassInstance = tc
    }
  }

  trait AllOps[A] extends Ops[A] {
    def typeClassInstance: CanTruthy[A]
  }

  object ops {
    implicit def toAllCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): AllOps[A] = new AllOps[A] {
      val self = target
      val typeClassInstance = tc
    }
  }
}

object MyApp2 extends App{
  import CanTruthy.ops._
  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.fromTruthy({
    case 0 => false
    case _ => true
  })

  10.truthy
}

