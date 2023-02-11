package PerfTestConfig

import PerfTestConfig.{baseUrl, durationMin, maxResponseTimeMs, meanResponseTimeMs, requestPerSecond}
import io.gatling.core.Predef.{StringBody, constantUsersPerSec, global, scenario, _}
import io.gatling.http.Predef.{http, status, _}
import scala.language.postfixOps

class GetGlobalSimulation extends Simulation {
  val httpConf = (PerfTestConfig.baseUrl)
  val httpProtocol = http
    .baseUrl(httpConf)
  val rootEndPointUsers = scenario("Root end point calls")
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
  def getAllComments: ChainBuilder = {
      exec(
        http("Get All Clothes")
          .get("/ropa")
          .check(status.in(200 to 304))
      )
  }

  def getAllPosts: ChainBuilder = {
      exec(
        http("Get All Travels")
          .get("/viajes")
          .check(status.in(200 to 304))
      )
  }

  //3. Scenario Definition
  val scn: ScenarioBuilder = scenario("Eight Scenario")
    .exec(getAllComments)
    .pause(5)
    .exec(getAllPosts)
    .pause(5)
    .exec(getAllComments)

  //4. Load Scenario
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(10) during (10 seconds),
      rampUsersPerSec(1) to (5) during (20 seconds))
  ).protocols(httpProtocol)
}