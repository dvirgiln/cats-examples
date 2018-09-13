package com.example.typeclasses

/**
  * Created by dave on 12/11/17.
  */


// Define a very simple JSON AST
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
// The "serialize to JSON" behavior is encoded in this trait
trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)
object JsonWriterInstances {
  implicit val stringJsonWriter = new JsonWriter[String] {
    def write(value: String): Json =
      JsString(value)
  }
  implicit val personJsonWriter = new JsonWriter[Person] {
    def write(value: Person): Json =
      JsObject(Map("name" -> JsString(value.name),
        "email" -> JsString(value.email)
      ))
  }
  // etc...
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

object JsonApp extends App{
  import JsonWriterInstances._
  println(Json.toJson(Person("Dave", "dave@example.com")))
}