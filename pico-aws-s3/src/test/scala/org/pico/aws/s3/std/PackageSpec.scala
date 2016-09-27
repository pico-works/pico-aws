package org.pico.aws.s3.std

import com.amazonaws.services.s3.AmazonS3Client
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "Support disposable syntax" in {
    for (sqsClient <- Auto(new AmazonS3Client())) {
      ok
    }
  }
}
