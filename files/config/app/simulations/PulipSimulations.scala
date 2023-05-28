package PerfTestConfig

import Pulip.{baseUrl2Deleteb, baseUrlPut3b, baseUrl1b, token, baseUrlAssets, baseUrlAssetsBody, baseUrl1, baseUrl2, baseUrlApi, baseUrlPut, baseUrl2Delete, baseUrlGet1, baseUrlGet2, baseUrlGet3, baseUrlPost1, baseUrlPut2, baseUrlPut3, durationMin, maxResponseTimeMs, meanResponseTimeMs, requestPerSecond, constantUsersPerSec, rampUsersPerSecsince, rampUsersPerSecStill, atOnceUser}
import io.gatling.core.Predef.{StringBody, constantUsersPerSec, global, scenario, _}
import io.gatling.http.Predef.{http, status, _}
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

import io.gatling.http.request.builder
import io.gatling.jdbc.Predef._

class PulipSimulationPostPutDelete extends Simulation {

  val httpProtocol = http.baseUrl(Pulip.baseUrl1b)

  // Datos para las solicitudes
    val Headers1 = Map(
    "authority" -> "api-stage.xxxxxxxxxxxxxxxx",
    "accept" -> "application/json, text/plain, */*",
    "accept-language" -> "es-ES,es;q=0.9,en;q=0.8",
    "authorization" -> Pulip.token,
    "cache-control" -> "no-cache",
    "content-type" -> "application/json",
    "origin" -> "https://bo-stage.xxxxxxxxxxxxx",
    "pragma" -> "no-cache",
    "referer" -> "https://bo-stage.xxxxxxxxxxxx/",
    "sec-ch-ua" -> """"Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> """"Linux"""",
    "sec-fetch-dest" -> "empty",
    "sec-fetch-mode" -> "cors",
    "sec-fetch-site" -> "same-site",
    "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
  )

  val putData = StringBody("""{"name":"patro 53","active":false,"description":"descmarcadepruebaparalaimpmasiivatestimp 243","landing":false,"link":{"desktop":"https://www.xxxxxxxxxxxxx.com/","mobile":"https://xxxxxxxxxxx.com/","tablet":"https://xxxxxxxxxxxx.com/"},"characteristics":[{"characteristic":323,"value":"https://xxxxxxxxxxxxxx.com/"},{"characteristic":324,"value":"https://xxxxxxxxxxxxxxxxxx.com/"},{"characteristic":320,"value":"andres@id4you.com"}],"sponsorships":[1163],"dataStudioLink":"","codeCRM":"","whitelistLoadMediaKits":false,"whitelistLoadPromotions":false,"flushDefaultLogo":false,"file":null,"extraImages":[]}""")
  val postData = StringBody("""{"name":"prueba test","active":false,"description":"","landing":false,"link":{"desktop":"","mobile":"","tablet":""},"characteristics":[{"characteristic":290,"value":"false"},{"characteristic":291,"value":"2023-04-13T00:00:00"},{"characteristic":292,"value":"false"},{"characteristic":296,"value":"false"},{"characteristic":297,"value":"false"},{"characteristic":298,"value":"false"},{"characteristic":299,"value":"false"},{"characteristic":300,"value":"false"},{"characteristic":301,"value":"false"},{"characteristic":302,"value":"false"},{"characteristic":303,"value":"false"},{"characteristic":304,"value":"false"},{"characteristic":305,"value":"false"},{"characteristic":306,"value":"false"},{"characteristic":307,"value":"false"},{"characteristic":308,"value":"false"},{"characteristic":309,"value":"false"},{"characteristic":310,"value":"false"},{"characteristic":338,"value":"false"},{"characteristic":340,"value":"false"},{"characteristic":341,"value":"false"}],"sponsorships":[1167],"dataStudioLink":"","codeCRM":"","whitelistLoadMediaKits":false,"whitelistLoadPromotions":false,"flushDefaultLogo":false,"file":null,"extraImages":[]}""")

  // Escenarios
  val putScenario = scenario("PUT Scenario")
    .exec(
      http("PUT Request")
        .put(Pulip.baseUrlPut3b)
        .headers(Headers1)
        .body(putData)
        .check(status.is(200))
    )

  val deleteScenario = scenario("DELETE Scenario")
    .exec(
      http("DELETE Request")
        .delete(Pulip.baseUrl2Deleteb)
        .headers(Headers1)
        .check(status.is(204))
    )

  val postScenario = scenario("POST Scenario")
    .exec(
      http("POST Request")
        .post(Pulip.baseUrlPost1b)
        .headers(Headers1)
        .body(postData)
        .check(status.is(201))
    )

  // Configuración de la simulación
  setUp(
    putScenario.inject(atOnceUsers(Pulip.atOnceUser)),
    deleteScenario.inject(atOnceUsers(Pulip.atOnceUser)),
    postScenario.inject(atOnceUsers(Pulip.atOnceUser))
  ).protocols(httpProtocol)
}

class PulipSimulationRandomSession extends Simulation {

  object randomStringGenerator {
    def randomString(length: Int) = scala.util.Random.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val req = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "<gpOBJECT>\n" +
      "<gpPARAM name=\"auth_method\">3</gpPARAM>\n" +
      "<gpPARAM name=\"app_url\">MY_APP</gpPARAM>\n" +
      "<gpPARAM name=\"log_session_id\">0000000000</gpPARAM>\n" +
      "<gpPARAM name=\"device_id\">b02edd23,ClientIP=10.211.55.3</gpPARAM>\n" +
      "<gpPARAM name=\"service\">ACTIVATION</gpPARAM>\n" +
    "</gpOBJECT>"

  var randomSession = Iterator.continually(Map("randsession" -> ( req.replace("0000000000", randomStringGenerator.randomString(50)))))

  val httpConf = http
    .baseUrl(Pulip.baseUrl2)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("Activate")
    .feed(randomSession)
    .exec(http("activate request")
    .post("login")
    .body(StringBody("""${randsession}"""))
    .basicAuth("email", "password")
    .check(status.is(200)))
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(Pulip.atOnceUser))
  ).protocols(httpConf)
}

class PulipSimulationGetAssets extends Simulation {

    val httpProtocol = http
        .baseUrl(Pulip.baseUrlAssets)
        .inferHtmlResources(BlackList(), WhiteList(""".*\.*"""))
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate, sdch")
        .acceptLanguageHeader("en-GB,en-US;q=0.8,en;q=0.6")
        .contentTypeHeader("application/x-www-form-urlencoded")
        .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")

    val Res1 = (Pulip.baseUrlAssets) + (Pulip.baseUrlAssetsBody)

    val scn = scenario("GETs")
        .exec(http("request_0")
            .get((Pulip.baseUrlAssetsBody))
            .check(bodyString.saveAs("Res1")))
        .exec {session =>
            println("Res1=> " + Res1)
            session
        }

    setUp(scn.inject(atOnceUsers(Pulip.atOnceUser))).protocols(httpProtocol)
}

class PulipSimulationPut extends Simulation {

  val httpProtocol = http.baseUrl(Pulip.baseUrl1b)

  val putHeaders = Map(
    "authority" -> "api-stage.xxxxxxxxxxxxxxx",
    "accept" -> "application/json, text/plain, */*",
    "accept-language" -> "es-ES,es;q=0.9,en;q=0.8",
    "authorization" -> Pulip.token,
    "cache-control" -> "no-cache",
    "content-type" -> "application/json",
    "origin" -> "https://bo-stage.xxxxxxxxxxxxxx",
    "pragma" -> "no-cache",
    "referer" -> "https://bo-stage.xxxxxxxxxxxxxxxxxx/",
    "sec-ch-ua" -> """"Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> """"Linux"""",
    "sec-fetch-dest" -> "empty",
    "sec-fetch-mode" -> "cors",
    "sec-fetch-site" -> "same-site",
    "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
  )

  val putData = StringBody("""{"name":"patro 53","active":false,"description":"descmarcadepruebaparalaimpmasiivatestimp 243","landing":false,"link":{"desktop":"https://www.xxxxxxx.com/","mobile":"https://xxxx.com/","tablet":"https://xxxxx.com/"},"characteristics":[{"characteristic":323,"value":"https://xxxxxxxxx.com/"},{"characteristic":324,"value":"https://xxxxxxxxxxxxxxx.com/"},{"characteristic":320,"value":"andres@id4you.com"}],"sponsorships":[1163],"dataStudioLink":"","codeCRM":"","whitelistLoadMediaKits":false,"whitelistLoadPromotions":false,"flushDefaultLogo":false,"file":null,"extraImages":[]}""")

  val putScenario = scenario("PUT Scenario")
    .exec(
      http("PUT Request")
        .put(Pulip.baseUrlPut3)
        .headers(putHeaders)
        .body(putData)
        .check(status.is(200))
    )

  setUp(
    putScenario.inject(atOnceUsers(Pulip.atOnceUser))
  ).protocols(httpProtocol)
}

class PulipSimulationGet extends Simulation {

    val httpProtocol = http
        .baseUrl(Pulip.baseUrlApi)
        .inferHtmlResources(BlackList(), WhiteList(""".*\.*"""))
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate, sdch")
        .acceptLanguageHeader("en-GB,en-US;q=0.8,en;q=0.6")
        .contentTypeHeader("application/x-www-form-urlencoded")
        .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")

    val putHeadersGet = Map(
      "authority" -> "api-stage.xxxxxxxxxxxxxxxx",
      "accept" -> "application/json, text/plain, */*",
      "accept-language" -> "es-ES,es;q=0.9,en;q=0.8",
      "authorization" -> Pulip.token,
      "cache-control" -> "no-cache",
      "content-type" -> "application/json",
      "origin" -> Pulip.baseUrl2,
      "pragma" -> "no-cache",
      "referer" -> Pulip.baseUrl2,
      "sec-ch-ua" -> """"Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"""",
      "sec-ch-ua-mobile" -> "?0",
      "sec-ch-ua-platform" -> """"Linux"""",
      "sec-fetch-dest" -> "empty",
      "sec-fetch-mode" -> "cors",
      "sec-fetch-site" -> "same-site",
      "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
    )

    val Res1 = (Pulip.baseUrlApi) + (Pulip.baseUrlGet2)
    val scn = scenario("GETs")
        .exec(http("request_0")
            .get((Pulip.baseUrlApi) + (Pulip.baseUrlGet2))
            .headers(putHeadersGet)
            .check(bodyString.saveAs("Res1")))
        .exec {session =>
            println("Res1=> " + Res1)
            session
        }

    setUp(scn.inject(atOnceUsers(Pulip.atOnceUser))).protocols(httpProtocol)
}

class PulipSimulationGetx3 extends Simulation {

  val httpProtocol = http.baseUrl(Pulip.baseUrl1b)

  // Datos para las solicitudes
    val Headers2 = Map(
    "authority" -> "api-stage.xxxxxxxxxxxxxx",
    "accept" -> "application/json, text/plain, */*",
    "accept-language" -> "es-ES,es;q=0.9,en;q=0.8",
    "authorization" -> Pulip.token,
    "cache-control" -> "no-cache",
    "content-type" -> "application/json",
    "origin" -> "https://bo-stage.xxxxxxxxxxxxxxxxxx",
    "pragma" -> "no-cache",
    "referer" -> "https://bo-stage.xxxxxxxxxxxxxx/",
    "sec-ch-ua" -> """"Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> """"Linux"""",
    "sec-fetch-dest" -> "empty",
    "sec-fetch-mode" -> "cors",
    "sec-fetch-site" -> "same-site",
    "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
  )

  // Escenarios
  val getScenario1 = scenario("GET Scenario 1")
    .exec(
      http("GET Request 1")
        .get(Pulip.baseUrlGet1)
        .headers(Headers2)
        .check(status.is(200))
    )

  val getScenario2 = scenario("GET Scenario 2")
    .exec(
      http("GET Request 2")
        .get(Pulip.baseUrlGet2)
        .headers(Headers2)
        .check(status.is(204))
    )

  val getScenario3 = scenario("GET Scenario 3")
    .exec(
      http("GET Request 3")
        .get(Pulip.baseUrlGet3)
        .headers(Headers2)
        .check(status.is(201))
    )

  // Configuración de la simulación
  setUp(
    getScenario1.inject(atOnceUsers(Pulip.atOnceUser)),
    getScenario2.inject(atOnceUsers(Pulip.atOnceUser)),
    getScenario3.inject(atOnceUsers(Pulip.atOnceUser))
  ).protocols(httpProtocol)
}
