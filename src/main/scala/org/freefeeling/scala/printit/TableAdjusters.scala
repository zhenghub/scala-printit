package org.freefeeling.scala.printit

import org.freefeeling.scala.printit.TableFormatter.{ColumnNamesGetter, Decoder2Table, Row2String, Table}
import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat.ToInt
import shapeless.ops.record._

/**
  * Created by zh on 16-11-24.
  */
trait SingleEleAdjuster {
  implicit def eleRS[E] = new Row2String[E] {
    override def apply(r: E): Seq[String] = Seq(String.valueOf(r))
  }

  implicit def colNames[E] = new ColumnNamesGetter[E] {
    override def apply(firstRow: Option[E]): Option[Seq[String]] = None
  }
}

object SingleEleAdjuster extends SingleEleAdjuster

trait TableAdjusters extends SingleEleAdjuster {

  implicit def seqDT[T](implicit rs: Row2String[T], columnNamesGetter: ColumnNamesGetter[T]) = new Decoder2Table[Seq[T]] {
    type R = T

    override def decode(c: Seq[T]): Table[Seq[T], R] = new Table[Seq[T], R] {
      val value = c

      override def columnNames: Option[Seq[String]] = columnNamesGetter.apply(c.headOption)

      override def showRows: Iterator[Seq[String]] = c.iterator.map(rs.apply(_))
    }
  }

  implicit def seqRS[T] = new Row2String[Seq[T]] {
    override def apply(r: Seq[T]): Seq[String] = r.map(String.valueOf(_))
  }

  implicit def hlistRS[L <: HList](implicit toTraversableAux: ToTraversable.Aux[L, List, Any]) = new Row2String[L] {
    override def apply(r: L): Seq[String] = r.toList[Any].map(String.valueOf(_))
  }

  implicit def tupeRS[L <: Product] = new Row2String[L] {
    override def apply(r: L): Seq[String] = r.productIterator.map(String.valueOf _).toSeq
  }

  /**
    * for case class and tuple, since TupleN are case classes
    *
    * @param aux
    * @param keys
    * @param tt
    * @tparam C
    * @tparam LL
    * @tparam KL
    * @return
    */
  implicit def caseClassColumnNames[C <: Product, LL <: HList, KL <: HList](implicit aux: LabelledGeneric.Aux[C, LL], keys: Keys.Aux[LL, KL], tt: ToTraversable.Aux[KL, List, Symbol]) = new ColumnNamesGetter[C] {
    def apply(firstRow: Option[C]): Option[Seq[String]] = Some(keys.apply().toList[Symbol].map(_.name))
  }

  implicit def hlistColumnNames[L <: HList, N <: Nat](implicit l: Length.Aux[L, N], ti: ToInt[N]) = new ColumnNamesGetter[L] {
    override def apply(firstRow: Option[L]): Option[Seq[String]] = {
      val length = ti.apply()
      if (length == 0)
        Some(Seq("0"))
      else
        Some((0 until length).map(_.toString))
    }
  }

  implicit def seqColumnNames[T] = new ColumnNamesGetter[Seq[T]] {
    override def apply(firstRow: Option[Seq[T]]): Option[Seq[String]] = {
      firstRow.map(_.length).map(l => (0 until l).map(_.toString))
    }
  }


}

object TableAdjusters extends TableAdjusters