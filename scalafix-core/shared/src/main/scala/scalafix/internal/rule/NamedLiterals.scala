package scalafix.internal.rule

import scalafix._
import scala.meta._

case class NamedLiterals(index: SemanticdbIndex) extends SemanticRule(index, "NamedLiterals") {
  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case q"true" =>
        println("TRUE!!")
    }
    Patch.empty
  }

}
