
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GatlingSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("application/json, text/javascript, */*; q=0.01")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("nb-NO,nb;q=0.8,no;q=0.6,nn;q=0.4,en-US;q=0.2,en;q=0.2")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Origin" -> "http://localhost:8080",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_2 = Map("Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8")

	val headers_3 = Map(
		"Content-Type" -> "application/json; charset=UTF-8",
		"Origin" -> "http://localhost:8080",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_4 = Map("X-Requested-With" -> "XMLHttpRequest")

	val headers_7 = Map(
		"Accept" -> "*/*",
		"Content-Type" -> "application/json; charset=UTF-8",
		"Origin" -> "http://localhost:8080",
		"X-Requested-With" -> "XMLHttpRequest")


	object Login {	
		val login = exec(http("request_0")
			.get("/NotSoSecureBank/index.html")
			.headers(headers_0)
			.resources(http("request_1")
			.delete("/NotSoSecureBank/webresources/session")
			.headers(headers_1),
            http("request_2")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400))))
		.pause(8)
		.exec(http("request_3")
			.post("/NotSoSecureBank/webresources/session")
			.headers(headers_3)
			.body(RawFileBody("ExampleSimulation_0003_request.txt"))
			.resources(http("request_4")
			.get("/NotSoSecureBank/webresources/session")
			.headers(headers_4),
            http("request_5")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400)),
            http("request_6")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_4)))
		.pause(3)
		.exec(http("request_7")
			.put("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_7)
			.body(RawFileBody("ExampleSimulation_0007_request.txt"))
			.resources(http("request_8")
			.get("/NotSoSecureBank/mypage.html")
			.headers(headers_0),
            http("request_9")
			.get("/NotSoSecureBank/webresources/session")
			.headers(headers_4),
            http("request_10")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400)),
            http("request_11")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_4)))
		.pause(4)
	}

	object View {	
		val view = exec(http("request_12")
			.get("/NotSoSecureBank/webresources/session")
			.headers(headers_4)
			.resources(http("request_13")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400)),
            http("request_14")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_4),
            http("request_15")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com/transaction")
			.headers(headers_4)))
		.pause(2)
		.exec(http("request_16")
			.get("/NotSoSecureBank/webresources/session")
			.headers(headers_4)
			.resources(http("request_17")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400)),
            http("request_18")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_4)))
		.pause(17)	
	}

	object Pay {	
		val pay = exec(http("request_19")
			.post("/NotSoSecureBank/webresources/user/hei@sveisen.com/transaction")
			.headers(headers_7)
			.body(RawFileBody("ExampleSimulation_0019_request.txt"))
			.resources(http("request_20")
			.get("/NotSoSecureBank/webresources/session")
			.headers(headers_4),
            http("request_21")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400)),
            http("request_22")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com")
			.headers(headers_4),
            http("request_23")
			.get("/NotSoSecureBank/webresources/user/hei@sveisen.com/transaction")
			.headers(headers_4)))
		.pause(12)
	}

	object Logout {	
		val logout = exec(http("request_24")
			.delete("/NotSoSecureBank/webresources/session")
			.headers(headers_1)
			.resources(http("request_25")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(400))))
	}
	
	val payers = scenario("Payers").exec(Login.login, View.view, Pay.pay, Logout.logout)
	val viewers = scenario("Viewers").exec(Login.login, View.view, Logout.logout)
		
	setUp(
		payers.inject(rampUsers(10) over (100 seconds)),
		viewers.inject(rampUsers(100) over (100 seconds))
	).protocols(httpProtocol)
}