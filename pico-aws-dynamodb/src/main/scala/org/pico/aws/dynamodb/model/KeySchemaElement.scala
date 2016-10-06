package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

case class KeySchemaElement(
    attributeName: String,
    keyType: String) {
  def toAws: aws.KeySchemaElement = {
    val that = new aws.KeySchemaElement()

    that.setAttributeName(attributeName)
    that.setKeyType(keyType)

    that
  }
}

object KeySchemaElement {
  def from(that: aws.KeySchemaElement): KeySchemaElement = {
    KeySchemaElement(
      that.getAttributeName,
      that.getKeyType)
  }
}
