package org.freefeeling.scala.printit

/**
  * Created by zh on 16-11-24.
  */
object TestUtils {

  implicit class StringFormat4Test(val s: String) extends AnyVal {
    def table = s.stripMargin.split("\n").map(_.trim).filter(_.length > 0)
  }

}