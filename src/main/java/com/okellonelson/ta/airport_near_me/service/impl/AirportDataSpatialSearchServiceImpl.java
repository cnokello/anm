package com.okellonelson.ta.airport_near_me.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import com.google.gson.Gson;

import com.okellonelson.ta.airport_near_me.service.SpatialSearchService;

public enum AirportDataSpatialSearchServiceImpl implements SpatialSearchService {
	INSTANCE;

	private SolrClient solrClient;
	private Gson gson = new Gson();

	private void setSolrClient(String solrServerUrl) throws Exception {
		if (solrClient == null)
			solrClient = new HttpSolrClient.Builder(solrServerUrl).build();
	}

	@Override
	public String search(Double lat, Double lon, String searchServerURL) throws Exception {
		if (solrClient == null)
			setSolrClient(searchServerURL);

		try {
			SolrQuery query = new SolrQuery();
			query.setFields("iata_code,lat_lon");
			query.set("q", "*:*");
			query.set("sfield", "lat_lon");
			query.set("pt", String.format("%s,%s", lat, lon));
			query.set("sort", "geodist() asc");
			query.set("fl", "iata_code, lat_lon, _dist_:geodist()");

			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			for (SolrDocument doc : solrClient.query(query).getResults()) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("iata_code", doc.get("iata_code"));
				result.put("lat_lon", doc.get("lat_lon"));
				result.put("distance", doc.get("_dist_"));
				
				results.add(result);
			}
			
			return gson.toJson(results);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String.format("Performed search on Lat: %s, Lon: %s", lat, lon);
	}
}
