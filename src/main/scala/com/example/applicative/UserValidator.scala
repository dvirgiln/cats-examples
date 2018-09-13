package com.example.applicative

/**
  * Created by dave on 27/08/18.
  */
import Domain._
import cats._
import cats.data._
import cats.implicits._
import cats.ApplicativeError
trait UserValidator[F[_]] {
  def createValidUser(name: String, age: Int, email: String): F[User]
}

object UserValidator {
  def apply[F[_]](implicit ev: UserValidator[F]): UserValidator[F] = ev


  def userValidator[F[_], E](mkError: UserValidationError => E)(
    implicit A: ApplicativeError[F, E]): UserValidator[F] =
    new UserValidator[F] {

      def validateName(name: String): F[String] =
        if (name.matches("(?i:^[a-z][a-z ,.'-]*$)")) name.pure[F]
        else A.raiseError(mkError(NameNotValid))

      def validateAge(age: Int): F[Int] =
        if (age >= 18 && age < 120) age.pure[F]
        else A.raiseError(mkError(AgeOutOfRange))

      def validateEmail(email: String): F[String] =
        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
          email.pure[F]
        else A.raiseError(mkError(EmailNotValid))

      def createValidUser(name: String, age: Int, email: String): F[User] = {
        (User.apply _).curried.pure[F].ap(validateName(name)).ap(validateAge(age)).ap(validateEmail(email))
      }
    }

  def validate[F[_]: UserValidator, E](name: String,
                                       age: Int,
                                       email: String): F[User] =
    UserValidator[F].createValidUser(name, age, email)

}