class PricingJob
{
  static triggers = {
    if ((!GrailsUtil.environment in ['test', 'development'])) {
      cron name: 'calculateAvePricingTrigger', cronExpression: "0 0 0 1 * ?", startDelay: 60 * 1000
    } else {
      simple name: 'calculateAvePricingTrigger', startDelay: 60 * 60 * 1000, repeatInterval: 24 * 60 * 60 * 1000
    }
  }

  def concurrent = false

  def priceService
  def standardConversionService

  def execute() {
    println "Executing Pricing Job"
    priceService.calculateRecipePrices()
  }

}