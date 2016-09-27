package org.pico.aws.sqs

import com.amazonaws.services.sqs.model.{Message, ReceiveMessageRequest, ReceiveMessageResult}

import scala.collection.JavaConverters._

class SqsMessageContext(
    val request: ReceiveMessageRequest,
    val result: ReceiveMessageResult,
    val message: Message) {
  lazy val sourceQueueUrl = SqsQueueUrl(request.getQueueUrl)
  lazy val messageId = SqsMessageId(message.getMessageId)
  lazy val receiptHandle = SqsReceiptHandle(message.getReceiptHandle)
  lazy val messageAttributes = message.getMessageAttributes.asScala.toMap
  lazy val messageAttributesMd5 = message.getMD5OfMessageAttributes
  lazy val body = SqsMessageBody(message.getBody)
  lazy val bodyMd5 = message.getMD5OfBody

  def sqsDecode[A](implicit ev: SqsDecode[A]): Either[Throwable, A] = ev.sqsDecode(this)
}
