import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class Basic extends Simulation {
        val httpProtocol = http
        .baseUrl("wwwurl")
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar"))
        setUp(scn.inject(atOnceUsers(requestcount))).protocols(protocolvariable)
        }