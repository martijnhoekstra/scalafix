package scalafix.tests.core

import scala.meta._
import scalafix.syntax._
import scalafix.tests.BuildInfo
import scalafix.util.SemanticdbIndex
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FunSuite
import scalafix.testkit.DiffAssertions

abstract class BaseSemanticTest(filename: String)
    extends FunSuite
    with BeforeAndAfterAll
    with DiffAssertions {
  private var _db: SemanticdbIndex = _
  private var _doc: Document = _
  implicit def index: SemanticdbIndex = _db
  def doc: Document = _doc
  def source: Source = doc.input.parse[Source].get

  override def beforeAll(): Unit = {
    _db =
      SemanticdbIndex.load(Classpath(AbsolutePath(BuildInfo.sharedClasspath)))
    _doc = _db.documents
      .find(_.input.label.contains(filename))
      .getOrElse {
        throw new IllegalArgumentException(
          s"No $filename.semanticdb file found! Files are ${_db.documents.map(_.input.label)}")
      }
  }
}
