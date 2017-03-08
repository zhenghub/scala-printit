package org.freefeeling.scala.printit

import org.scalatest.FlatSpec
import TableFormatter._
import TestUtils._
import TableAdjusters._

/**
  * Created by zh on 16-11-24.
  */
class TestTableFormatter extends FlatSpec{

  "A TableFormatter" should "format a Seq[Int]" in {
    val dt: Decoder2Table[Seq[Int]] = seqDT[Int]
    assertResult(
      """
      |+---+
      || 1 |
      || 2 |
      |+---+""".table
    )(Seq(1, 2).tstr.toSeq)
  }

  it should "also format a Seq[String]" in {
    assertResult(
      """
        |+-------+
        || hello |
        || world |
        |+-------+
      """.table
    )("hello world".split(" ").toSeq.tstr.toSeq)
  }

  it should "also format a Seq[Seq[String]]" in {
    Seq("hello world".split(" ").toSeq).pta
    assertResult(
      """+-------+-------+
        || hello | world |
        |+-------+-------+""".table
    )(Seq("hello world".split(" ").toSeq).tstr.toSeq)
  }

}
