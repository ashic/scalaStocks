
import org.scalatest.{FlatSpec, Matchers}

class CacheSpec extends FlatSpec with Matchers {

  behavior of classOf[Cache[_, _]].getName

  class TestOrigin {
    var called = 0

    def query(key:String) : String = {
      called += 1
      s"Called so far: $called. key: $key"
    }
  }


  it should "query origin if not in cache" in new Context {

    val actual = cache.fetch("foo").runA(Map.empty).value

    actual shouldEqual "Called so far: 1. key: foo"
    origin.called shouldEqual 1

  }

  it should "not query origin if value is already stockTicker.cached" in new Context {
    val (first, second) = (for {
      first <- cache.fetch("foo")
      second <- cache.fetch("foo")
    } yield (first, second)).runA(Map.empty).value

    first shouldEqual "Called so far: 1. key: foo"
    second shouldEqual first
    origin.called shouldEqual 1
  }

  it should "query origin for different keys" in new Context {
    val (first, second) = (for {
      first <- cache.fetch("foo")
      second <- cache.fetch("bar")
    } yield (first, second)).runA(Map.empty).value

    first shouldEqual "Called so far: 1. key: foo"
    second shouldEqual "Called so far: 2. key: bar"
    origin.called shouldEqual 2

  }

  sealed trait Context {

    val origin = new TestOrigin
    val cache = new Cache[String, String](origin.query)

  }

}
