import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class inyectarUsuariosInmediatamente extends Simulation {
        val httpProtocol = http
        .baseUrl("wwwurl")
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar"))
// inyectar X usuarios inmediatamente
        setUp(scn.inject(atOnceUsers(requestcount))).protocols(protocolvariable)
        }

class inyectarUsuariosDistribuidosEnMinutos extends Simulation {
        val httpProtocol = http
        .baseUrl("wwwurl")
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar"))
// inyectar X usuarios en un periodo de X minutos de manera distribuida
       setUp(scn.inject(rampUsers(requestcount) during (timevariable minutes))).protocols(protocolvariable)
        }