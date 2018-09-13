package com.example.eq

/**
  * Created by dave on 12/11/17.
  */

import cats.Eq
import cats.syntax.eq._

final case class Cat(name: String, age: Int, color: String)

object EqExample extends App{
  implicit val catEqual = Eq.instance[Cat] { (cat1, cat2) =>
    import cats.instances.int._
    import cats.instances.string._
    (cat1.name === cat2.name ) &&
      (cat1.age === cat2.age
        ) &&
      (cat1.color === cat2.color)
  }


  val cat1 = Cat("Garfield",
    35, "orange and black")
  // cat1: Cat = Cat(Garfield,35,orange and black)
  val cat2 = Cat("Heathcliff", 30, "orange and black")
  // cat2: Cat = Cat(Heathcliff,30,orange and black)
  println(cat1 === cat2)
  // res14: Boolean = false
    println(cat1 =!= cat2)
  // res15: Boolean = true
  import cats.instances.option._
  val optionCat1 = Option(cat1)
  import cats.instances.int._
  val optionCat2 = Option.empty[Cat]
  // optionCat2: Option[Cat] = None
  println(optionCat1 === optionCat2)
  // res16: Boolean = false
    println(optionCat1 =!= optionCat2)
  // res17: Boolean = true


  //Using Option in a wrong way

  //Some(1) === None is not compiling

  Option(1) === Option.empty[Int]
}


