package PerfTestConfig

import PerfTestConfig.{baseUrl, durationMin, maxResponseTimeMs, meanResponseTimeMs, requestPerSecond}

import scala.concurrent.duration._

// 2
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicSimulation extends Simulation { // 3

  val httpConf = (PerfTestConfig.baseUrl)
  val httpProtocol = http
    .baseUrl(httpConf) // 4
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("es-AR,es;q=0.8,en-US;q=0.5,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("Nubity Stress Test Basic Simulation") // 7
    .exec(http("request_1") // 8
      .get("/")) // 9
    .pause(5) // 10

  setUp( // 11
    scn.inject(atOnceUsers(PerfTestConfig.requestPerSecond)) // 12
  ).protocols(httpProtocol) // 13
  .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}

class inyectarUsuariosConcurrentemente extends Simulation {
        val httpConf = (PerfTestConfig.baseUrl)
        val httpProtocol = http
        .baseUrl(httpConf)
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar").check(status.is(httpcodelogin)))
// inyectar X usuarios inmediatamente
        setUp(scn.inject(atOnceUsers(requestPerSecond))).protocols(protocolvariable)
        }

class inyectarUsuariosDistribuidosEnMinutos extends Simulation {
        val httpConf = (PerfTestConfig.baseUrl)
        val httpProtocol = http
        .baseUrl(httpConf)
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar").check(status.is(httpcodelogin)))
// inyectar X usuarios en un periodo de X minutos de manera distribuida
       setUp(scn.inject(rampUsers(requestPerSecond) during (timevariable minutes))).protocols(protocolvariable)
        }

class testLoginUsuariosDistribuidosEnMinutos extends Simulation {

    val httpConf = (PerfTestConfig.baseUrl)
    val httpProtocol = http
        .baseUrl(httpConf)
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        .acceptLanguageHeader("es-AR,es;q=0.8,en-US;q=0.5,en;q=0.3")
        .acceptEncodingHeader("gzip, deflate, br")
        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
    val userCount =requestPerSecond
    val rampUpTime =tiempologin seconds
    val testDuration =maxiempologin minutes

    val loginScenario = scenario("Login")
        .exec(http("login_request")
            .post("getVar")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("usernamelogin", "secretusernamelogin")
            .formParam("passwordlogin", "secretpasswordlogin!")
            .check(status.is(httpcodelogin))
            .check(regex("""(?i)ERROR: msjerrorlogin""").count.is(0))
        )

    setUp(
        loginScenario.inject(atOnceUsers(userCount))
    ).protocols(httpProtocol)
    .maxDuration(testDuration)
    .assertions(
      global.responseTime.max.lt(PerfTestConfig.meanResponseTimeMs),
      global.responseTime.mean.lt(PerfTestConfig.maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}