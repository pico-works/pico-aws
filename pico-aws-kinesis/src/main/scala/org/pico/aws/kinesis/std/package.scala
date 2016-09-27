package org.pico.aws.kinesis

import com.amazonaws.services.kinesis.{AmazonKinesis, AmazonKinesisClient}
import org.pico.disposal.Disposable

package object std {
  implicit val disposable_AmazonKinesis_e87HZpt = Disposable[AmazonKinesis](_.shutdown())

  implicit val disposable_AmazonKinesisClient_e87HZpt = Disposable[AmazonKinesisClient](_.shutdown())
}
