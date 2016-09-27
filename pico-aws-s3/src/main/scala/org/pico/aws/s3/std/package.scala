package org.pico.aws.s3

import com.amazonaws.services.s3.AmazonS3Client
import org.pico.disposal.Disposable

package object std {
  implicit val disposable_AmazonS3Client_e87HZpt = Disposable[AmazonS3Client](_.shutdown())
}
