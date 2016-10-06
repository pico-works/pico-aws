package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

case class ProvisionedThroughput(
    readCapacityUnits: Long = 0L,
    writeCapacityUnits: Long = 0L) {
  def toAws: aws.ProvisionedThroughput = {
    val that = new aws.ProvisionedThroughput()

    that.setReadCapacityUnits(readCapacityUnits)
    that.setWriteCapacityUnits(writeCapacityUnits)

    that
  }
}

object ProvisionedThroughput {
  def from(that: aws.ProvisionedThroughput): ProvisionedThroughput = {
    ProvisionedThroughput(
      that.getReadCapacityUnits,
      that.getWriteCapacityUnits)
  }
}
