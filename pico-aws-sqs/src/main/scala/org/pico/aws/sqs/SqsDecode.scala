package org.pico.aws.sqs

import cats.Monad

import scala.annotation.tailrec

trait SqsDecode[A] {
  def sqsDecode(sqsMessageContext: SqsMessageContext): Either[SqsDecodeException, A]
}

object SqsDecode {
  implicit val monad_SqsDecode_WB2v6or = new Monad[SqsDecode] {
    override def flatMap[A, B](fa: SqsDecode[A])(f: A => SqsDecode[B]): SqsDecode[B] = {
      SqsDecode { sqsMessageContext =>
        fa.sqsDecode(sqsMessageContext) match {
          case Right(value) => f(value).sqsDecode(sqsMessageContext)
          case Left(error) => Left(error)
        }
      }
    }

    override def tailRecM[A, B](a: A)(f: A => SqsDecode[Either[A, B]]): SqsDecode[B] = {
      new SqsDecode[B] {
        @tailrec
        private [this] def step(
            sqsMessageContext: SqsMessageContext, a1: A): Either[SqsDecodeException, B] = {
          f(a1).sqsDecode(sqsMessageContext) match {
            case l @ Left(_) => l.asInstanceOf[Either[SqsDecodeException, B]]
            case Right(Left(a2)) => step(sqsMessageContext, a2)
            case Right(Right(b)) => Right(b)
          }
        }

        final def sqsDecode(sqsMessageContext: SqsMessageContext): Either[SqsDecodeException, B] = {
          step(sqsMessageContext, a)
        }
      }
    }

    override def pure[A](x: A): SqsDecode[A] = SqsDecode(_ => Right(x))
  }

  def apply[A](f: SqsMessageContext => Either[SqsDecodeException, A]): SqsDecode[A] = {
    new SqsDecode[A] {
      override def sqsDecode(sqsMessageContext: SqsMessageContext): Either[SqsDecodeException, A] = {
        f(sqsMessageContext)
      }
    }
  }
}
