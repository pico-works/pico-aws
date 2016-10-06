package org.pico.aws.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import org.pico.aws.dynamodb.model.CreateTableRequest

case class DynamoDbClient(impl: AmazonDynamoDBAsyncClient) {
  def createTable(request: CreateTableRequest): Unit = impl.createTable(request.toAws)
}
