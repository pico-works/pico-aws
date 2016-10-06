package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

import scala.collection.JavaConverters._

case class GlobalSecondaryIndex(
    indexName: String,
    keySchema: List[KeySchemaElement],
    projection: Projection,
    provisionedThroughput: ProvisionedThroughput) {
  def toAws: aws.GlobalSecondaryIndex = {
    val that = new aws.GlobalSecondaryIndex()

    that.setIndexName(indexName)
    that.setKeySchema(keySchema.map(_.toAws).asJava)
    that.setProjection(projection.toAws)
    that.setProvisionedThroughput(provisionedThroughput.toAws)

    that
  }
}

object GlobalSecondaryIndex {
  def from(that: aws.GlobalSecondaryIndex): GlobalSecondaryIndex = {
    GlobalSecondaryIndex(
      that.getIndexName,
      that.getKeySchema.asScala.map(KeySchemaElement.from).toList,
      Projection.from(that.getProjection),
      ProvisionedThroughput.from(that.getProvisionedThroughput))
  }
}
