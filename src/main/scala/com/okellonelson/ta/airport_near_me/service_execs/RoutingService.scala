package com.okellonelson.ta.airport_near_me.service_execs

import scala.concurrent.duration._
import akka.util.Timeout
import akka.actor.ActorRef
import akka.event.Logging
import akka.event.LoggingAdapter
import spray.routing._
import spray.http.{ HttpMethods, HttpMethod, HttpResponse, AllOrigins }
import spray.http.HttpHeaders._
import spray.http.ContentTypes._
import spray.http.HttpMethods._
import akka.actor.{ Actor, ActorContext }
import spray.http.StatusCodes._
import org.json4s.Formats
import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import com.okellonelson.ta.aiport_near_me.Cfg

import spray.routing.directives.ParamDefMagnet.apply

object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}

trait RoutingService extends HttpService {
  this: HttpService =>

  val cfg = Cfg.getCfg
  private val allowOriginHeader = `Access-Control-Allow-Origin`(AllOrigins)
  private val optionsCorsHeaders = List(
    `Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent, sid"),
    `Access-Control-Expose-Headers`("sid"),
    `Access-Control-Max-Age`(1728000))

  def cors[T]: Directive0 = mapRequestContext { ctx =>
    ctx.withRouteResponseHandling({
      case Rejected(x) if (ctx.request.method.equals(HttpMethods.OPTIONS) && !x.filter(_.isInstanceOf[MethodRejection]).isEmpty) => {
        val allowedMethods: List[HttpMethod] = x.filter(_.isInstanceOf[MethodRejection]).map(rejection => {
          rejection.asInstanceOf[MethodRejection].supported
        })
        ctx.complete(HttpResponse().withHeaders(
          `Access-Control-Allow-Methods`(OPTIONS, allowedMethods: _*) :: allowOriginHeader ::
            optionsCorsHeaders))
      }
    }).withHttpResponseHeadersMapped { headers =>
      allowOriginHeader :: headers

    }
  }

  val searchService: ActorRef
  val logger: LoggingAdapter

  def responWithSessionId(sessionId: String) =
    respondWithHeader(RawHeader("sid", sessionId))

  import scala.collection.JavaConversions._
  import scala.collection.JavaConverters._
  implicit def executionContext = actorRefFactory.dispatcher
  implicit val timeout = Timeout(5 seconds)

  import Json4sProtocol._
  import akka.pattern.ask
  import com.okellonelson.ta.airport_near_me.service_execs.SearchService.GetNearestAirport

  val routes = cors {
    path("airports" / "near_me") {
      get {
        parameters('lat, 'lon) { (lat, lon) =>
          complete {
            (searchService ? GetNearestAirport(lat.toDouble, lon.toDouble)).mapTo[String]
          }
        }
      }
    }
  }

}

class RoutingServices(searchServiceActor: ActorRef) extends Actor with RoutingService {
  override val logger = Logging(context.system, this)
  override val searchService: ActorRef = searchServiceActor

  def actorRefFactory: ActorContext = context
  def receive: Actor.Receive = runRoute(routes)
}
