package com.okellonelson.ta.aiport_near_me

import java.io._
import java.util._
import scala.collection.JavaConverters._

object Cfg {
  var cfg: scala.collection.mutable.Map[String, String] = null

  def getCfg: scala.collection.mutable.Map[String, String] = {
    if (cfg == null || cfg.isEmpty) {
      val props = new Properties
      props.load(Cfg.getClass.getClassLoader.getResourceAsStream("airports_near_me.properties"))
      cfg = props.asScala
    }

    return cfg
  }

  def getProperties: Properties = {
    if (cfg == null || cfg.isEmpty) getCfg

    (new Properties /: cfg) {
      case (a, (k, v)) =>
        a.put(k, v)
        a
    }
  }
}
