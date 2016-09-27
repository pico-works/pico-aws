package org.pico.aws.sqs.std

import com.amazonaws.services.sqs.AmazonSQSClient
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "Support disposable syntax" in {
    for (sqsClient <- Auto(new AmazonSQSClient())) {
      ok
    }
  }
}
