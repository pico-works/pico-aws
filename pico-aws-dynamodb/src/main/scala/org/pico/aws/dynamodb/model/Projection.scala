package org.pico.aws.dynamodb.model

import com.amazonaws.services.dynamodbv2.{model => aws}

import scala.collection.JavaConverters._

case class Projection(
    projectionType: String,
    nonKeyAttributes: List[String]) {
  def toAws: aws.Projection = {
    val that = new aws.Projection

    that.setProjectionType(projectionType)
    that.setNonKeyAttributes(nonKeyAttributes.asJava)

    that
  }
}

object Projection {
  def from(that: aws.Projection): Projection = {
    Projection(
      that.getProjectionType,
      that.getNonKeyAttributes.asScala.toList)
  }
}
