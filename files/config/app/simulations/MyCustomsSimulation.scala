package PerfTestConfig

import PerfTestConfig.{baseUrl, durationMin, maxResponseTimeMs, meanResponseTimeMs, requestPerSecond}
import io.gatling.core.Predef.{StringBody, constantUsersPerSec, global, scenario, _}
import io.gatling.http.Predef.{http, status, _}
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class GetGlobalSimulation extends Simulation {
  val httpConf = (PerfTestConfig.baseUrl)
  val httpProtocol = http
    .baseUrl(httpConf)
  val rootEndPointUsers = scenario("Nubity Stress Test Get Global Simulation")
    .exec(http("root end point")
      .get("/")
      .header("Content-Type", "application/json")
      .header("Accept-Encoding", "gzip")
      .body(StringBody("{}"))
      .check(status.is(200))
    )
  setUp(rootEndPointUsers.inject(
    constantUsersPerSec(PerfTestConfig.requestPerSecond) during (PerfTestConfig.durationMin))
    .protocols(httpProtocol))
    .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}

class RampUsersLoadSimulation extends Simulation {

  //1. HTTP Configuration
  val httpConf = (PerfTestConfig.baseUrl)
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(httpConf)
    .header("Accept", "application/json")

  //2. Call Definitions
  def getAllClothes: ChainBuilder = {
      exec(
        http("Get All Clothes")
          .get("/ropa")
          .check(status.in(200 to 304))
      )
  }

  def getAllTravels: ChainBuilder = {
      exec(
        http("Get All Travels")
          .get("/viajes")
          .check(status.in(200 to 304))
      )
  }

  def getAllelectrodomesticos: ChainBuilder = {
      exec(
        http("Get All Electrodomesticos")
          .get("/electrodomesticos")
          .check(status.in(200 to 304))
      )
  }

  //3. Scenario Definition
  val scn: ScenarioBuilder = scenario("Nubity Stress Test Ropa - Viajes- Electrodomesticos")
    .exec(getAllClothes)
    .pause(5)
    .exec(getAllTravels)
    .pause(5)
    .exec(getAllelectrodomesticos)

  //4. Load Scenario
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(PerfTestConfig.constantUsersPerSec) during (10 seconds),
      rampUsersPerSec(PerfTestConfig.rampUsersPerSecsince) to (PerfTestConfig.rampUsersPerSecStill) during (20 seconds))
  ).protocols(httpProtocol)
  .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}

class RampUsersLoadSimulationAssets extends Simulation {

  //1. HTTP Configuration
  val httpConf = (PerfTestConfig.baseUrl2)
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(httpConf)
    .header("Accept", "application/json")

  //2. Call Definitions
  def getAllAssets: ChainBuilder = {
      exec(
        http("Get Assets Images 1")
          .get(PerfTestConfig.getAllAssets)
          .check(status.in(200 to 304))
      )
  }

  def getAllAssets2: ChainBuilder = {
      exec(
        http("Get Assets Images 2")
          .get(PerfTestConfig.getAllAssets2)
          .check(status.in(200 to 304))
      )
  }

  def getAllAssets3: ChainBuilder = {
      exec(
        http("Get Assets Images 3")
          .get(PerfTestConfig.getAllAssets3)
          .check(status.in(200 to 304))
      )
  }

  //3. Scenario Definition
  val scn: ScenarioBuilder = scenario("Nubity Stress Test Assets")
    .exec(getAllAssets)
    .pause(5)
    .exec(getAllAssets2)
    .pause(5)
    .exec(getAllAssets3)

  //4. Load Scenario
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(PerfTestConfig.constantUsersPerSec) during (10 seconds),
      rampUsersPerSec(PerfTestConfig.rampUsersPerSecsince) to (PerfTestConfig.rampUsersPerSecStill) during (20 seconds))
  ).protocols(httpProtocol)
  .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}