package com.okellonelson.ta.airport_near_me.service;

import com.okellonelson.ta.airport_near_me.model.Airport;

public interface IndexingService {
	void index(Airport airport) throws Exception;

}
