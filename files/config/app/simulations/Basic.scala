import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class inyectarUsuariosConcurrentemente extends Simulation {
        val httpProtocol = http
        .baseUrl("wwwurl")
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar").check(status.is(httpcodelogin)))
// inyectar X usuarios inmediatamente
        setUp(scn.inject(atOnceUsers(requestcount))).protocols(protocolvariable)
        }

class inyectarUsuariosDistribuidosEnMinutos extends Simulation {
        val httpProtocol = http
        .baseUrl("wwwurl")
        val scn = scenario("scenarioName")
        .exec(http("request_ok").get("getVar").check(status.is(httpcodelogin)))
// inyectar X usuarios en un periodo de X minutos de manera distribuida
       setUp(scn.inject(rampUsers(requestcount) during (timevariable minutes))).protocols(protocolvariable)
        }

class testLoginUsuariosDistribuidosEnMinutos extends Simulation {

    val httpProtocol = http
        .baseUrl("wwwurl")
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .acceptEncodingHeader("gzip, deflate")
        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
    val userCount =requestcount
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
}