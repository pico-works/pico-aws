package org.pico.aws.dynamodb

import java.util.concurrent.ExecutorService

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
import com.amazonaws.services.dynamodbv2.{model => aws}
import org.pico.aws.core.Async
import org.pico.aws.dynamodb.model.{BatchGetItemRequest, CreateTableRequest}

import scala.concurrent.{ExecutionContext, Future}

case class DynamoDbClient(impl: AmazonDynamoDBAsyncClient) {
  def executorService: ExecutorService = impl.getExecutorService

  def batchGetItem(
      request: BatchGetItemRequest)(
      implicit ec: ExecutionContext): Future[(BatchGetItemRequest, BatchGetItemResult)] = {
    Async.handle[aws.BatchGetItemRequest, BatchGetItemResult] { handler =>
      impl.batchGetItemAsync(request.toAws, handler)
    } map { case (req, res) =>
      BatchGetItemRequest.from(req) -> res
    }
  }

  def createTable(request: CreateTableRequest): Unit = impl.createTable(request.toAws)
}
