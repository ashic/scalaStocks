package stockTicker


object DemoApp extends App {

  import Yahoo._

  val pricing = new StockPricing

  import pricing._

  val googleDailyPrices = dailyPrices("GOOG")
  val googleDailyReturns = returns("GOOG")
  val googleAverageReturns = meanReturn("GOOG")

  println (s"Daily Prices: $googleDailyPrices")
  println (s"Daily Returns: ${googleDailyReturns.toList}")
  println (s"Average Returns: $googleAverageReturns")

}









