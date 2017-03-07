package com.okellonelson.ta.airport_near_me.service_execs

import akka.actor.Actor
import akka.actor.actorRef2Scala
import akka.event.Logging
import akka.event.LoggingAdapter

trait DataPreparationService {

  val logger: LoggingAdapter

  def indexData(webUrl: String, indexingServerUrl: String) = {
    import com.okellonelson.ta.airport_near_me.service.impl.AirportDataIngestionService
    import com.okellonelson.ta.airport_near_me.service.impl.AirportDataIndexingService
    new AirportDataIngestionService(
      webUrl, new AirportDataIndexingService(indexingServerUrl)).ingest()
  }

}

class DataPreparationServices extends Actor with DataPreparationService {
  import DataPreparationService._
  import com.okellonelson.ta.airport_near_me.service.IndexingService
  import com.okellonelson.ta.airport_near_me.service.impl.AirportDataIndexingService
  override val logger = Logging(context.system, this)

  def receive = {
    case WebIngest(webUrl, indexingServerUrl) => {
      indexData(webUrl, indexingServerUrl)
      sender ! "Ingestion successful."
    }

    case _ => sender ! "Invalid request!"
  }

}

object DataPreparationService {
  case class WebIngest(webUrl: String, indexingServerUrl: String)

}