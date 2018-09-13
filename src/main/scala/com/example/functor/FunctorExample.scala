package com.example.functor

/**
  * Created by dave on 12/11/17.
  */

//https://stackoverflow.com/questions/39197422/scala-sbt-run-unsupported-major-minor-version-52-0
//NICE LINK TO SHOW HOW TO CHANGE THE DEFAULT JRE
object FunctorExample extends App{
  import cats.instances.function._
  import cats.syntax.functor._
  val func1 = (x: Int) => x.toDouble
  val func2 = (x: Double) => x*2
  //val func3=func1.map(func2)   // THIS LINE works fine from sbt. This is related to the partial Unification


  //import scala.language.higherKinds
  //Useed to avoid scala compile warnings when we use a higher kind Functor[F[_]]

}
