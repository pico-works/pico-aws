package org.pico.aws.sqs

case class SqsEncodedMessage(
    queueUrl: Option[String] = None,
    body: Option[SqsMessageBody] = None)
