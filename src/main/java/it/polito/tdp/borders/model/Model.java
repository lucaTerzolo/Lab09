package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	public Graph<Country,DefaultEdge> grafo;
	private BordersDAO dao;
	private Map <Integer, Country> idMap;
	
	public Model() {
		dao=new BordersDAO();
		idMap=new HashMap<Integer,Country>();
	}

	public void creaGrafo(int anno) {
		
		grafo=new SimpleGraph<>(DefaultEdge.class);
		
		dao.loadAllCountries(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		List<Border> coppiaConfini=dao.getCountryPairs(anno);
		for(Border b:coppiaConfini) {
			grafo.addEdge(idMap.get(b.getState1no()), idMap.get(b.getState2no()));
		}
		System.out.print(grafo.vertexSet().size());
		System.out.print("\nedge "+ grafo.edgeSet().size());
	}
	
	
	
}
