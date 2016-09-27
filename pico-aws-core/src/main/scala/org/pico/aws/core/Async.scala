package org.pico.aws.core

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler

import scala.concurrent.{Future, Promise}

object Async {
  def handle[X <: AmazonWebServiceRequest, Y](f: AsyncHandler[X, Y] => Unit): Future[(X, Y)] = {
    val p = Promise[(X, Y)]

    f(new AsyncHandler[X, Y] {
      override def onError(exception: Exception): Unit = p.failure(exception)

      override def onSuccess(request: X, result: Y): Unit = p.success(request -> result)
    })

    p.future
  }
}
