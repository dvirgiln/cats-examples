package com.example.applicative

/**
  * Created by dave on 27/08/18.
  */
object Domain {
  final case class User(
                         name: String,
                         age: Int,
                         email: String
                       )

  sealed trait UserValidationError
  // defined trait UserValidationError

  case object NameNotValid extends UserValidationError
  // defined object NameNotValid

  case object AgeOutOfRange extends UserValidationError
  // defined object AgeOutOfRange

  case object EmailNotValid extends UserValidationError
  // defined object EmailNotValid
}
