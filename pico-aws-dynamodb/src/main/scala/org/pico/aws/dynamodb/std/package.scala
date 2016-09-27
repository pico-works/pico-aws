package org.pico.aws.dynamodb

import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClient}
import org.pico.disposal.Disposable

package object std {
  implicit val disposable_AmazonDynamoDB_e87HZpt = Disposable[AmazonDynamoDB](_.shutdown())

  implicit val disposable_AmazonDynamoDBClient_e87HZpt = Disposable[AmazonDynamoDBClient](_.shutdown())
}
