package com.example.applicative

import cats._
import cats.data._
import cats.implicits._

import scala.util.Try

object UserValidatorIdMain extends App{
  import Domain._
  val userValidatorIdInterpreter = new UserValidator[Id] {
    def createValidUser(name: String, age: Int, email: String): Id[User] =
      User(name, age, email)
  }

  val userValidatorOptionInterpreter =
    UserValidator.userValidator[Option, Unit](_ => ())
  // userValidatorOptionInterpreter: UserValidator[Option] = $anon$1@7e284fb6

  val userValidatorTryInterpreter =
    UserValidator.userValidator[Try, Throwable](err => new Throwable(err.toString))

  val userValidatorEitherInterpreter =
    UserValidator.userValidator[Either[UserValidationError, ?], UserValidationError](identity)

  val userValidatorValidatedInterpreter =
    UserValidator.userValidator[Validated[NonEmptyList[UserValidationError], ?],
      NonEmptyList[UserValidationError]](NonEmptyList(_, Nil))


  implicit val userValidatorInterpreter = userValidatorValidatedInterpreter


  println(UserValidator.validate("John", 25, "john@example.com"))
  // User(John,25,john@example.com)

  println(UserValidator.validate("John", 25, "johnn@example"))
  // User(John,25,johnn@example)

  println(UserValidator.validate("John", -1, "john@gexample"))
  // User(John,-1,john@gexample)

  println(UserValidator.validate(".John", -1, "john@gexample"))
  // User(.John,-1,john@gexample)



}

