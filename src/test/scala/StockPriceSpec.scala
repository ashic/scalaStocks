import java.text.SimpleDateFormat

import org.scalatest.{FlatSpec, Matchers}
import stockTicker.StockPrice

class StockPriceSpec extends FlatSpec with Matchers {

  behavior of StockPrice.getClass.getName

  it should "parse comma seperated line" in {

    val line = "2017-01-13,807.47998,811.223999,806.690002,807.880005,1090100,807.880005"

    val price = StockPrice("GOOG", line)


    price shouldEqual StockPrice("GOOG", dateFormat.parse("2017-01-13"), 807.880005)
  }

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
}
