package org.pico.aws.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import com.amazonaws.services.dynamodbv2.model.{CreateTableRequest, StreamSpecification}

case class DynamoDbClient(impl: AmazonDynamoDBAsyncClient) {
  def createTable(): Unit = {
    val request = new CreateTableRequest()

    val streamSpecification = new StreamSpecification()

    request.setStreamSpecification(streamSpecification)

    impl.createTable(request)
  }
}
