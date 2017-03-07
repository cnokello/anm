package com.okellonelson.ta.airport_near_me.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface SpatialSearchService {
	String search(Double lat, Double lon, String searchServerURL) throws Exception;
}
