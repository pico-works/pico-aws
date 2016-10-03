package org.pico.aws.sqs

import cats.syntax.functor._

case class UpperCased(value: String)

object UpperCased {
  implicit val sqsDecode_UpperCased_tcD87sq = {
    SqsDecode.on[SqsMessageBody].map(v => UpperCased(v.body.toUpperCase()))
  }
}
