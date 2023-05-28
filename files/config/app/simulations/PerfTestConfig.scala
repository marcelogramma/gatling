package PerfTestConfig

object PerfTestConfig {
  val baseUrl = "https://www.xxxxxxxxxxxxxxx.xxx"
  val baseUrl2 = "https://assets.xxxxxxxxxxxxx.xxxx" // RampUsersLoadSimulationAssets
  val requestPerSecond = 100
  val durationMin = 2 // solo para el test GetGlobalSimulation en minutos
  val meanResponseTimeMs = 600
  val maxResponseTimeMs = 200
  val constantUsersPerSec = 10 // solo para el test RampUsersLoadSimulation y RampUsersLoadSimulationAssets
  val rampUsersPerSecsince = 1 // solo para el test RampUsersLoadSimulation y RampUsersLoadSimulationAssets
  val rampUsersPerSecStill = 5 // solo para el test RampUsersLoadSimulation y RampUsersLoadSimulationAssets
  val getAllAssets = "/assets/2022/imgs/icons/xxxxxxxxxxx.png" // solo para el test RampUsersLoadSimulationAssets
  val getAllAssets2 = "/assets/2022/share.png" // solo para el test RampUsersLoadSimulationAssets
  val getAllAssets3 = "/assets/2022/imgs/icons/favicon_16x16.png" // solo para el test RampUsersLoadSimulationAssets
  val atOnceUser = 15 //solo para el test pause and check status simulation
}

//################################################################################
//
//            PULIP
//################################################################################

object Pulip {
  val baseUrlAssets = "https://assets-stage.xxxxxxxxxxx"
  val baseUrlAssetsBody = "/public/hotsale-mx/imgs/logo-xxxxxxxxxxxxxxxxxxxx" 
  val baseUrl1 = "https://api-stage.xxxxxxxxxxxxxxxxxxxxxxxxxxx"
  val baseUrl1b = "https://api-stage.xxxxxxxxxxxxxxxxxxxxxxx/"  
  val baseUrl2 = "https://bo-stage.xxxxxxxxxxxxxxxx"
  val baseUrlApi = "https://api-stage.pxxxxxxxxxxxxxxxxx"
  val baseUrlPut = "https://api-stage.xxxxxxxxxxxxxxxxxxxxx"
  val baseUrlPut2 = "https://api-stage.xxxxxxxxxxxxxxxxxxxxxxxxxxxx"
  val baseUrlPut3 = "/events/2/editions/47/sponsors/2395"
  val baseUrlPut3b = "/events/2/editions/47/sponsors/2395"
  val baseUrl2Delete = "/events/2/editions/47/sponsors/2395"
  val baseUrl2Deleteb = "/events/2/editions/47/sponsors/2396"
  val baseUrlGet1 = "/events/2/editions/47/sponsors?query=ebay&size=100&filter=Cintillo%20en%20Buscador-sponsorshipFilter&filter=Promoci%C3%B3n%20Afirme-characteristicFilter&filter=Activo-stateFilter95"
  val baseUrlGet2 = "/events/2/editions/47/sponsors?size=100"
  val baseUrlGet3 = "/events/2/editions/47/sponsors/2395"
  val baseUrlPost1 = "/events/2/editions/47/sponsors"
  val baseUrlPost1b = "events/2/editions/47/sponsors"
  val requestPerSecond = 100
  val durationMin = 2
  val meanResponseTimeMs = 600
  val maxResponseTimeMs = 200
  val constantUsersPerSec = 10
  val rampUsersPerSecsince = 1
  val rampUsersPerSecStill = 5
  val atOnceUser = 150
  val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJlc3MtcHJ1ZWJhc0A0MmkuY28iLCJpYXQiOjE2ODM4OTM5MDAsInZlcnNpb24iOiIxLjAuNCIsImxhc3RBY3Rpdml0eSI6IjIwMjMtMDUtMTJUMTI6MTg6MjAuODI4OTQ0In0.BRCqoisOAoumhriSvxLOyVrkVhonfwA_KbK7Pj6HOoQ"
}
