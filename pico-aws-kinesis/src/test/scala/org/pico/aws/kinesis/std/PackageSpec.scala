package org.pico.aws.kinesis.std

import com.amazonaws.services.kinesis.AmazonKinesisClient
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "Support disposable syntax" in {
    for (sqsClient <- Auto(new AmazonKinesisClient())) {
      ok
    }
  }
}
