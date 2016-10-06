package org.pico.aws.dynamodb

import java.nio.ByteBuffer

import com.amazonaws.services.dynamodbv2.{model => aws}
import scala.collection.JavaConverters._

case class AttributeValue(
    s: String,
    n: String,
    b: ByteBuffer,
    sS: List[String],
    nS: List[String],
    bS: List[ByteBuffer],
    m: Map[String, AttributeValue],
    l: List[AttributeValue],
    nullValue: Boolean,
    bool: Boolean) {
  def toAws: aws.AttributeValue = {
    val that = new aws.AttributeValue()

    that.setS(s)
    that.setN(n)
    that.setB(b)
    that.setSS(sS.asJava)
    that.setNS(nS.asJava)
    that.setBS(bS.asJava)
    that.setM(m.mapValues(_.toAws).asJava)
    that.setL(l.map(_.toAws).asJava)
    that.setNULL(nullValue)
    that.setBOOL(bool)

    that
  }
}
