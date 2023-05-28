package PerfTestConfig

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import PerfTestConfig.{baseUrl, durationMin, maxResponseTimeMs, meanResponseTimeMs, requestPerSecond, atOnceUser}

import scala.concurrent.duration.DurationInt

class PauseAndCheckStatusSimulation extends Simulation {

  //1. HTTP Configuration
  val httpProtocol: HttpProtocolBuilder = http
//  val httpConf = (PerfTestConfig.baseUrl)
    .baseUrl(PerfTestConfig.baseUrl)
    .header("Accept", "application/json")

  //2. Scenario Definition
  val scn: ScenarioBuilder = scenario("Nubity Stress Test Pause and Check Status")
    .exec(http("Get All /")
      .get("/")
      .check(status.is(200)))
    .pause(5)

    .exec(http("Get Specific target")
      .get("/ropa")
      .check(status.in(200 to 210)))
    .pause(1, 10)

    .exec(http("Get All Posts - 2nd call")
      .get("/viajes")
      .check(status.not(404), status.not(500)))
    .pause(2000.milliseconds)

  //3. Load Scenario
  setUp(
    scn.inject(atOnceUsers(PerfTestConfig.atOnceUser))
  ).protocols(httpProtocol)
  .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}