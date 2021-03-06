package scalafix.tests.reflect

import org.scalatest.FunSuite
import scalafix.internal.reflect.RuleInstrumentation
import metaconfig.Configured
import org.langmeta.inputs.Input

class RuleInstrumentationTest extends FunSuite {
  def check(name: String, original: String, expected: List[String]): Unit = {
    test(name) {
      val Configured.Ok(obtained) =
        RuleInstrumentation.getRuleFqn(Input.VirtualFile(name, original))
      assert(obtained == expected)
    }
  }

  check(
    "lenient dialect is supported",
    """
      |package a
      |import scalafix._
      |object MyRule extends Rule("MyRule") {
      |  List(
      |    1,
      |    2,
      |  )
      |}
    """.stripMargin,
    List("a.MyRule")
  )
}
