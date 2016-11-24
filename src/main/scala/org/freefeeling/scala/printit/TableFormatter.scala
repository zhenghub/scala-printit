package org.freefeeling.scala.printit

import org.freefeeling.scala.printit.TableFormatter.{Decoder2Table, Row2String, Table}

/**
  * Created by zh on 16-11-24.
  */
trait TableFormatter {

  def splitSize: Int = 100

  def format[T, RE <: Decoder2Table[T]](container: T)(implicit extractor: RE, rowStringer: Row2String[RE#R]): Iterator[String] = {
    val table = extractor.decode(container)
    val head = table.columnNames
    val rows = table.rows.map(rowStringer.stringify)
    format(head, rows)
  }

  protected def fixLength(str: String, length: Int) = {
    if (str.length == length)
      " " + str + " "
    else
      " " + str + " " * (length - str.length) + " "
  }

  protected def _format(sizes: Array[Int], header: Option[Seq[String]], rows: Iterator[Seq[String]]): Iterator[String] = {

    val rowGroups = rows.sliding(splitSize, splitSize)

    val firstGroup = rowGroups.next()

    firstGroup.foreach { r =>
      r.zipWithIndex.foreach { case (s, idx) =>
        if (sizes(idx) < s.length)
          sizes(idx) = s.length
      }
    }

    val newFG = firstGroup.map(_.zipWithIndex.map(vi => fixLength(vi._1, sizes(vi._2))))

    var splitLine = ("+" + sizes.map(l => "-" * (l + 2)).mkString("+") + "+")

    val newHeaders = header
      .map(_.zipWithIndex.map(vi => fixLength(vi._1, sizes(vi._2))))
      .map("|" + _.mkString("|") + "|")
      .map { hLine =>
        Seq(splitLine, hLine, splitLine)
      }.getOrElse(Seq(splitLine)).iterator

    newHeaders ++ newFG.iterator.map("|" + _.mkString("|") + "|") ++ rowGroups.flatMap { g =>
      g.foreach { r =>
        r.zipWithIndex.foreach { case (s, idx) =>
          if (sizes(idx) < s.length)
            sizes(idx) = s.length
        }
      }
      g.map(_.zipWithIndex.map(vi => fixLength(vi._1, sizes(vi._2)))).map("|" + _.mkString("|") + "|")
    } ++ Seq("+" + sizes.map(l => "-" * (l + 2)).mkString("+") + "+").iterator

  }

  def format(header: Option[Seq[String]], rows: Iterator[Seq[String]]): Iterator[String] = {
    val sizes = header.map(_.map(_.length).toArray)
    if (sizes.isEmpty) {
      if (rows.hasNext) {
        val fr = rows.next()
        val s = fr.map(_.length).toArray
        _format(s, header, Seq(fr).iterator ++ rows)
      } else {
        Iterator.empty
      }
    } else {
      _format(sizes.get, header, rows)
    }

  }

}

object TableFormatter {

  val defaultTF = new TableFormatter{

  }

  trait Decoder2Table[C] {
    type R
    def decode(c: C): Table[C, R]
  }

  trait Table[C, R] {

    val value: C

    def columnNames: Option[Seq[String]]

    def rows: Iterator[R]

  }

  trait Row2String[R] {
    def stringify(r: R): Seq[String]
  }

  implicit class TablePrint[T](val v: T) extends AnyVal {
    def table[DT <: Decoder2Table[T]](implicit extractor: DT, rowStringer: Row2String[DT#R]) = {
      defaultTF.format(v)
    }

    def pta[DT <: Decoder2Table[T]](implicit extractor: DT, rowStringer: Row2String[DT#R]) = {
      table.foreach(println)
    }
  }

}
