package org.pico.aws.sqs

case class SqsMessageEnvelope[+A](context: SqsMessageContext, value: A)
