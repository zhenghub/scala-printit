package org.freefeeling.scala.printit

import org.scalatest.FlatSpec
import TableFormatter._
import TestUtils._
import TableAdjusters._

/**
  * Created by zh on 16-11-24.
  */
class TestTableFormatter extends FlatSpec{

  "A TableFormatter" should "format a seq" in {
    assertResult(
      """
      |+---+
      || 0 |
      |+---+
      || 1 |
      |+---+""".table
    )(Seq(1).table.toSeq)
  }

}
