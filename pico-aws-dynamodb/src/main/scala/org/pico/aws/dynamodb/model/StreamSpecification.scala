package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.model.{StreamSpecification => AwsStreamSpecification}

case class StreamSpecification(
    streamEnabled: Boolean,
    streamViewType: String) {
  def toAws: AwsStreamSpecification = {
    val that = new AwsStreamSpecification()

    that.setStreamEnabled(streamEnabled)
    that.setStreamViewType(streamViewType)

    that
  }
}

object StreamSpecification {
  def from(that: AwsStreamSpecification): StreamSpecification = {
    StreamSpecification(
      streamEnabled   = that.getStreamEnabled,
      streamViewType  = that.getStreamViewType)
  }
}
