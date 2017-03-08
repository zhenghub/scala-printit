package org.freefeeling.scala.printit

import org.freefeeling.scala.printit.TableFormatter.{Decoder2Table, Row2String, Table}

/**
  * Created by zh on 16-11-24.
  */
trait TableAdjusters {

  implicit def seqDT[T] = new Decoder2Table[Seq[T]] {
    type R = T

    override def decode(c: Seq[T]): Table[Seq[T], R] = new Table[Seq[T], R] {
      val value = c

      override def columnNames: Option[Seq[String]] = None

      override def rows: Iterator[R] = value.iterator
    }
  }

  implicit val intRS = new Row2String[Int] {
    override def apply(r: Int): Seq[String] = Seq(String.valueOf(r))
  }

  implicit val stringRS = new Row2String[String] {
    override def apply(r: String): Seq[String] = Seq(r)
  }

  implicit def seqRS[T](implicit ers: Row2String[T]) = new Row2String[Seq[T]] {
    override def apply(r: Seq[T]): Seq[String] = r.flatMap(e => ers.apply(e))
  }

}

object TableAdjusters extends TableAdjusters