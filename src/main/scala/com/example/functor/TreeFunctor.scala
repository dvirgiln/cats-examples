package com.example.functor

import cats.Functor

/**
  * Created by dave on 12/11/17.
  */

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A])
  extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]


object TreeFunctor {
  implicit val treeFunctor = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =
      tree match {
        case Branch(left, right) =>
          Branch(map(left)(func), map(right)(func))
        case Leaf(value) =>
          Leaf(func(value))
      }
  }
}

object TreeFunctorApp extends App{
  import TreeFunctor._
  import cats.Functor
  import cats.syntax.functor._
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)
  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  leaf(100).map(_ * 2)
  branch(leaf(10), leaf(20)).map(_ * 2)
}