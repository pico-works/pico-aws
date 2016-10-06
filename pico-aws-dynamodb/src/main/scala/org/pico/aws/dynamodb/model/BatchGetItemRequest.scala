package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

import scala.collection.JavaConverters._

case class BatchGetItemRequest(
    requestItems: Map[String, KeysAndAttributes],
    returnConsumedCapacity: String) {
  def toAws: aws.BatchGetItemRequest = {
    val that = new aws.BatchGetItemRequest()

    that.setRequestItems(requestItems.mapValues(_.toAws).asJava)
    that.setReturnConsumedCapacity(returnConsumedCapacity)

    that
  }
}

object BatchGetItemRequest {
  def from(that: aws.BatchGetItemRequest): BatchGetItemRequest = {
    BatchGetItemRequest(
      that.getRequestItems.asScala.mapValues(KeysAndAttributes.from).toMap,
      that.getReturnConsumedCapacity)
  }
}
