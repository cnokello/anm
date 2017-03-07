package com.okellonelson.ta.airport_near_me.service.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.okellonelson.ta.airport_near_me.service.IndexingService;
import com.okellonelson.ta.airport_near_me.model.Airport;

public class AirportDataIndexingService implements IndexingService {

	private SolrClient solrClient;

	public AirportDataIndexingService(String solrUrl) {
		this.solrClient = new HttpSolrClient.Builder(solrUrl).build();
	}

	public void index(Airport airport) throws Exception {
		System.out.print(String.format("Ready to index: %s ... ", airport.toString()));
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("lat_lon", String.format("%s,%s", airport.getLat(), airport.getLon()));
		doc.addField("iata_code", airport.getCode());
		System.out.println("Done.");
		solrClient.add(doc);
		solrClient.commit();
	}

}