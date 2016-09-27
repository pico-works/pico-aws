package org.pico.aws.sqs

case class SqsDecodeException(message: String) extends Exception(message, null, true, false)
