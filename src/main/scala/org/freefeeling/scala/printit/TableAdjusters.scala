package org.freefeeling.scala.printit

import org.freefeeling.scala.printit.TableFormatter.{Decoder2Table, Row2String, Table}
import shapeless._

/**
  * Created by zh on 16-11-24.
  */
trait HListAdjusters extends Poly1 {
    implicit def caseInt = at[Int](x => String.valueOf(x))
    implicit def caseString = at[String](x => x)

    implicit val caseNil = at[HNil](_ => Seq[String]())
    implicit def caseHList[H, L <: HList](implicit hE2s: Case.Aux[H, String], tR2s: Case.Aux[L, Seq[String]]) = at[H :: L](t => hE2s(t.head) +: tR2s(t.tail))
}

trait TableAdjusters extends HListAdjusters {

  implicit def seqDT[T] = new Decoder2Table[Seq[T]] {
    type R = T

    override def decode(c: Seq[T]): Table[Seq[T], R] = new Table[Seq[T], R] {
      val value = c

      override def columnNames: Option[Seq[String]] = None

      override def rows: Iterator[R] = value.iterator
    }
  }

  implicit def eleRS[E](implicit hR2s: Case.Aux[E, String]) = new Row2String[E] {
    override def apply(r: E): Seq[String] = Seq(hR2s(r))
  }

//  implicit val stringRS = new Row2String[String] {
//    override def apply(r: String): Seq[String] = Seq(r)
//  }

  implicit def seqRS[T](implicit ers: Row2String[T]) = new Row2String[Seq[T]] {
    override def apply(r: Seq[T]): Seq[String] = r.flatMap(e => ers.apply(e))
  }

  implicit def hlistRS[L <: HList](implicit hR2s: Case.Aux[L, Seq[String]]) = new Row2String[L] {
    override def apply(r: L): Seq[String] = hR2s(r)
  }

}

object TableAdjusters extends TableAdjusters