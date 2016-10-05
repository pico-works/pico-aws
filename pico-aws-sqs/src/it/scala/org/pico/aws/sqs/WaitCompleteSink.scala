package org.pico.aws.sqs

import org.pico.disposal.SimpleDisposer
import org.pico.event.Sink

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait WaitCompleteSink[-A] extends Sink[Future[A]] with AutoCloseable

object WaitCompleteSink {
  def apply[A]: WaitCompleteSink[A] = {
    new WaitCompleteSink[A] with SimpleDisposer {
      private val lock = new Object
      private var done = false
      private var inFlight = 0L

      this.onClose {
        lock synchronized {
          done = true

          while (inFlight > 0L) {
            lock.wait()
          }
        }
      }

      override def publish(event: Future[A]): Unit = {
        lock synchronized {
          if (!done) {
            inFlight += 1
          }
        }

        event.onComplete { completion =>
          lock synchronized {
            inFlight -= 1
            lock.notifyAll()
          }
        }
      }
    }
  }
}
