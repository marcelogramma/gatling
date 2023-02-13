package PerfTestConfig

object PerfTestConfig {
  val baseUrl = "https://www.xxxxxxxxxxxx.com.ar"
  val baseUrl2 = "https://assets.xxxxxxxxxxxx.com.ar"
  val requestPerSecond = 100
  val durationMin = 2 // solo para el test GetGlobalSimulation
  val meanResponseTimeMs = 600
  val maxResponseTimeMs = 200
  val constantUsersPerSec = 10 // solo para el test RampUsersLoadSimulation y RampUsersLoadSimulationAssets
  val rampUsersPerSecsince = 1 // solo para el test RampUsersLoadSimulation  y RampUsersLoadSimulationAssets
  val rampUsersPerSecStill = 5 // solo para el test RampUsersLoadSimulation y RampUsersLoadSimulationAssets
  val getAllAssets = "/assets/2022/imgs/icons/xxxxxxxxxxxx.svg" // solo para el test RampUsersLoadSimulationAssets
  val getAllAssets2 = "/assets/2022/share.png" // solo para el test RampUsersLoadSimulationAssets
  val getAllAssets3 = "/assets/2022/imgs/icons/favicon_16x16.png" // solo para el test RampUsersLoadSimulationAssets
  val atOnceUser = 15 //solo para el test pause and check status simulation
}