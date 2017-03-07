package com.okellonelson.ta.aiport_near_me

import akka.actor.{ ActorSystem, Props }
import scala.concurrent.duration._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import akka.actor.actorRef2Scala
import com.okellonelson.ta.airport_near_me.service_execs.RoutingServices
import com.okellonelson.ta.airport_near_me.service_execs.DataPreparationServices
import com.okellonelson.ta.airport_near_me.service_execs.DataPreparationService.WebIngest
import com.okellonelson.ta.airport_near_me.service_execs.SearchServices

object Boot {

  val cfg = Cfg.getCfg

  def main(args: Array[String]) {
    implicit val actorSystem = ActorSystem("airports-near-me-system")
    implicit val timeout = Timeout(60 seconds)

    val searchService = actorSystem.actorOf(Props(new SearchServices(cfg("solr.url"))), "search-services")
    val service = actorSystem.actorOf(Props(new RoutingServices(searchService)), name = "routing-services")
    val dataPreparationService = actorSystem.actorOf(Props[DataPreparationServices], "data-preparation-services")

    if (cfg("preprocessor.run").trim().toInt == 1) {
      System.out.println("Performing preprocessing...");
      dataPreparationService ? WebIngest(cfg("airports.data.url"), cfg("solr.url"))
    }

    IO(Http) ! Http.Bind(service, interface = cfg("host.int"), cfg("host.port").toInt)
  }

}

