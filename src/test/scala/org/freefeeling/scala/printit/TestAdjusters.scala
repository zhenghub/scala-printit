package org.freefeeling.scala.printit

import org.scalatest.FlatSpec
import TableAdjusters._
import org.freefeeling.scala.printit.TableFormatter.Row2String
import shapeless.LabelledGeneric.Aux
import shapeless._

/**
  * Created by zh on 17-3-9.
  */
class TestAdjusters extends FlatSpec{

  "An Row2String instance for a HList" should "exists" in {
    assertCompiles("implicitly[Row2String[HNil]]")
    assertCompiles("implicitly[Row2String[Int :: HNil]]")
  }

}
