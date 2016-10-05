package org.pico.aws.sqs

case class SqsMessageBody(body: String) extends AnyVal

object SqsMessageBody {
  implicit val sqsDecodeSqsMessageBody = SqsDecode[SqsMessageBody] { sqsMessageContext =>
    Right(sqsMessageContext.body)
  }

  implicit val sqsEncodeSqsMessageBody = SqsEncode[SqsMessageBody] { value =>
    SqsEncodedMessage(body = Some(value))
  }
}
