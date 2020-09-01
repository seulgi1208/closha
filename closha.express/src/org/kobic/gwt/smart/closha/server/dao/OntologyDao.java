package org.kobic.gwt.smart.closha.server.dao;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;

public interface OntologyDao {
	
	Object addOntology(OntologyModel ontologyModel);
	void deleteOntology(List<String> ontologyList);
	List<OntologyModel> getOntology(int type, boolean isAdmin);
	int getOntologyCount(String ontologyID, int type, boolean isAdmin);
	
}
