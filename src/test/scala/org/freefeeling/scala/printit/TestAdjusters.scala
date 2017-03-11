package org.freefeeling.scala.printit

import org.scalatest.FlatSpec
import org.freefeeling.scala.printit.TableFormatter.{ColumnNamesGetter, Decoder2Table, Row2String, Table}
import TableAdjusters._
import org.freefeeling.scala.printit.TableFormatter.Row2String
import org.freefeeling.scala.printit.TestUtils.Person
import shapeless.LabelledGeneric.Aux
import shapeless._

/**
  * Created by zh on 17-3-9.
  */
class TestAdjusters extends FlatSpec{

  "Row2String" should "have an instance for a HList" in {
    assertCompiles("implicitly[Row2String[HNil]]")
    assertCompiles("implicitly[Row2String[Int :: HNil]]")
  }

  it should "also should have an instance for a tuple" in {
    assertCompiles("implicitly[Row2String[(String, Int)]]")
  }

  "ColumnNamesGetter" should "have an instance for a HList" in {
    val cng = implicitly[ColumnNamesGetter[HNil]]
    assertResult(Some(Seq("0")))(cng(Some(HNil)))

    assertResult(Some(Seq("0", "1")))(implicitly[ColumnNamesGetter[String :: Int:: HNil]].apply(Some("j" :: 12:: HNil)))
  }

  it should "have an instance for a tuple" in {
    val cng = implicitly[ColumnNamesGetter[(String, Int)]]
    assertResult(Some(Seq("_1", "_2")))(cng.apply(Some(("12", 1))))
  }

  it should "have an instande for a case class instance" in {
    val cng = implicitly[ColumnNamesGetter[Person]]
    assertResult(Some(Seq("name", "age")))(cng.apply(None))
  }

}
