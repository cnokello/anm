package com.okellonelson.ta.airport_near_me.service_execs

import akka.actor.{ Actor }
import akka.event.Logging
import com.okellonelson.ta.airport_near_me.service.SpatialSearchService
import com.okellonelson.ta.airport_near_me.service.impl.AirportDataSpatialSearchServiceImpl

trait SearchService

class SearchServices(searchServerURL: String) extends Actor with SearchService {
  val logger = Logging(context.system, this)
  val airportSearchService: SpatialSearchService = AirportDataSpatialSearchServiceImpl.INSTANCE

  import SearchService.GetNearestAirport
  import collection.JavaConversions._

  def receive = {
    case GetNearestAirport(lat, lon) => {
      sender ! airportSearchService.search(lat, lon, searchServerURL)
    }
    case _ => sender ! "Invalid request provided."
  }

}

object SearchService {
  case class GetNearestAirport(lat: Double, lon: Double)
}