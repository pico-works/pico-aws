package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}
import scala.collection.JavaConverters._

case class KeysAndAttributes(
    keys: List[Map[String, AttributeValue]],
    attributesToGet: List[String],
    consistentRead: Boolean,
    projectionExpression: String,
    expressionAttributeNames: Map[String, String]) {
  def toAws: aws.KeysAndAttributes = {
    val that = new aws.KeysAndAttributes()

    that.setKeys(keys.map(_.mapValues(_.toAws).asJava).asJava)
    that.setAttributesToGet(attributesToGet.asJava)
    that.setConsistentRead(consistentRead)
    that.setProjectionExpression(projectionExpression)
    that.setExpressionAttributeNames(expressionAttributeNames.asJava)

    that
  }
}

object KeysAndAttributes {
  def from(that: aws.KeysAndAttributes): KeysAndAttributes = {
    KeysAndAttributes(
        that.getKeys.asScala.map(_.asScala.mapValues(AttributeValue.from).toMap).toList,
        that.getAttributesToGet.asScala.toList,
        that.getConsistentRead,
        that.getProjectionExpression,
        that.getExpressionAttributeNames.asScala.toMap)
  }
}
