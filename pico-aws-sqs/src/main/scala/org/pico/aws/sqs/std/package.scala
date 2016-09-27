package org.pico.aws.sqs

import com.amazonaws.services.sqs.{AmazonSQS, AmazonSQSClient}
import org.pico.disposal.Disposable

package object std {
  implicit val disposable_AmazonSQS_e87HZpt = Disposable[AmazonSQS](_.shutdown())

  implicit val disposable_AmazonSQSClient_e87HZpt = Disposable[AmazonSQSClient](_.shutdown())
}
