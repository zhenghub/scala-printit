package org.freefeeling.scala.printit

import org.freefeeling.scala.printit.TableFormatter.{Decoder2Table, Row2String, Table}

/**
  * Created by zh on 16-11-24.
  */
trait TableAdjusters {

  implicit val seqIntDT = new Decoder2Table[Seq[Int]] {
    type R = Int

    override def decode(c: Seq[Int]): Table[Seq[Int], Int] = new Table[Seq[Int], Int] {
      val value = c

      override def columnNames: Option[Seq[String]] = Some(Seq("0"))

      override def rows: Iterator[R] = value.iterator
    }
  }

  implicit val intRS = new Row2String[Int] {
    override def stringify(r: Int): Seq[String] = Seq(String.valueOf(r))
  }

}

object TableAdjusters extends TableAdjusters