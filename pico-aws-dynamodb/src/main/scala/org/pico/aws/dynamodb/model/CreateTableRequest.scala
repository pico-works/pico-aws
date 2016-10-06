package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}
import scala.collection.JavaConverters._

case class CreateTableRequest(
    attributeDefinitions: List[AttributeDefinition],
    tableName: String,
    keySchema: List[KeySchemaElement],
    localSecondaryIndexes: List[LocalSecondaryIndex],
    globalSecondaryIndexes: List[GlobalSecondaryIndex],
    provisionedThroughput: ProvisionedThroughput,
    streamSpecification: StreamSpecification) {
  def toAws: aws.CreateTableRequest = {
    val request = new aws.CreateTableRequest()

    request.setAttributeDefinitions(attributeDefinitions.map(_.toAws).asJava)
    request.setTableName(tableName)
    request.setKeySchema(keySchema.map(_.toAws).asJava)
    request.setLocalSecondaryIndexes(localSecondaryIndexes.map(_.toAws).asJava)
    request.setGlobalSecondaryIndexes(globalSecondaryIndexes.map(_.toAws).asJava)
    request.setProvisionedThroughput(provisionedThroughput.toAws)
    request.setStreamSpecification(streamSpecification.toAws)

    request
  }
}

object CreateTableRequest {
  def from(that: aws.CreateTableRequest): CreateTableRequest = {
    CreateTableRequest(
        that.getAttributeDefinitions.asScala.map(AttributeDefinition.from).toList,
        that.getTableName,
        that.getKeySchema.asScala.map(KeySchemaElement.from).toList,
        that.getLocalSecondaryIndexes.asScala.map(LocalSecondaryIndex.from).toList,
        that.getGlobalSecondaryIndexes.asScala.map(GlobalSecondaryIndex.from).toList,
        ProvisionedThroughput.from(that.getProvisionedThroughput),
        StreamSpecification.from(that.getStreamSpecification))
  }
}
