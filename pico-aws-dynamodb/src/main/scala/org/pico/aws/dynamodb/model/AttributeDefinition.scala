package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

case class AttributeDefinition(
    attributeName: String,
    attributeType: String) {
  def toAws: aws.AttributeDefinition = {
    val that = new aws.AttributeDefinition()

    that.setAttributeName(attributeName)
    that.setAttributeType(attributeType)

    that
  }
}

object AttributeDefinition {
  def from(that: aws.AttributeDefinition): AttributeDefinition = {
    AttributeDefinition(
      that.getAttributeName,
      that.getAttributeType)
  }
}
