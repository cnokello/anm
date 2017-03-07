package com.okellonelson.ta.airport_near_me.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.okellonelson.ta.airport_near_me.service.IngestionService;
import com.okellonelson.ta.airport_near_me.service.IndexingService;
import com.okellonelson.ta.airport_near_me.model.Airport;

public class AirportDataIngestionService implements IngestionService {

	private String webUrl;
	private IndexingService indexingService;

	public AirportDataIngestionService(String webUrl, IndexingService indexingService) {
		this.webUrl = webUrl;
		this.indexingService = indexingService;
	}

	@Override
	public void ingest() throws Exception {
		System.out.println("Ready to download file from: " + webUrl);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new GZIPInputStream(new URL(webUrl).openStream())));

		String line = null;
		Long recordCount = 0L;

		while ((line = reader.readLine()) != null) {
			if (recordCount > 0) {
				String[] coords = line.split(",");
				indexingService.index(
						new Airport(coords[0], Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
			}
			recordCount++;
		}

		reader.close();
	}

}
