package org.pico.aws.dynamodb.std

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "Support disposable syntax" in {
    for (sqsClient <- Auto(new AmazonDynamoDBClient())) {
      ok
    }
  }
}
