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
    import org.freefeeling.scala.printit.SingleEleAdjuster._
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
    // Seq("hello world".split(" ").toSeq).pta
    assertResult(
      """+-------+-------+
        || 0     | 1     |
        |+-------+-------+
        || hello | world |
        |+-------+-------+""".table
    )(Seq("hello world".split(" ").toSeq).tstr.toSeq)
  }

  it should "also foramt a seq of case class" in {
    val testCase = Seq(Person("liC", 12), Person("JC", 25))
    assertResult(
      """+------+-----+
        || name | age |
        |+------+-----+
        || liC  | 12  |
        || JC   | 25  |
        |+------+-----+""".table
    )(testCase.tstr.toSeq)
  }

  it should "also format a seq of tuple" in {
    val testCase = Seq(("li", 12), ("J", 25))
    testCase.pta
    assertResult(
      """+----+----+
        || _1 | _2 |
        |+----+----+
        || li | 12 |
        || J  | 25 |
        |+----+----+""".table
    )(testCase.tstr.toSeq)
  }

}
