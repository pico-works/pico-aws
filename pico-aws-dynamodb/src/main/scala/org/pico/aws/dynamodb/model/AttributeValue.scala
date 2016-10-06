package org.pico.aws.dynamodb.model

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
    nULLValue: Boolean,
    bOOL: Boolean) {
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
    that.setNULL(nULLValue)
    that.setBOOL(bOOL)

    that
  }
}

object AttributeValue {
  def from(that: aws.AttributeValue): AttributeValue = {
    AttributeValue(
      that.getS,
      that.getN,
      that.getB,
      that.getSS.asScala.toList,
      that.getNS.asScala.toList,
      that.getBS.asScala.toList,
      that.getM.asScala.mapValues(AttributeValue.from).toMap,
      that.getL.asScala.map(AttributeValue.from).toList,
      that.getNULL,
      that.getBOOL)
  }
}
