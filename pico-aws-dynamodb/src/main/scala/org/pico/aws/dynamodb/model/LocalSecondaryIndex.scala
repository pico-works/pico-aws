package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

import scala.collection.JavaConverters._

case class LocalSecondaryIndex(
    indexName: String,
    keySchema: List[KeySchemaElement],
    projection: Projection = null) {
  def toAws: aws.LocalSecondaryIndex = {
    val that = new aws.LocalSecondaryIndex()

    that.setIndexName(indexName)
    that.setKeySchema(keySchema.map(_.toAws).asJava)
    that.setProjection(projection.toAws)

    that
  }
}

object LocalSecondaryIndex {
  def from(that: aws.LocalSecondaryIndex): LocalSecondaryIndex = {
    LocalSecondaryIndex(
      that.getIndexName,
      that.getKeySchema.asScala.map(KeySchemaElement.from).toList,
      Projection.from(that.getProjection))
  }
}
