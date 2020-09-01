package org.kobic.gwt.smart.closha.server.service;

import java.util.HashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.server.dao.OntologyDao;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class OntologyService implements OntologyDao{

	@SuppressWarnings("unused")
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Object addOntology(OntologyModel ontologyModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("ontology.addOntology", ontologyModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OntologyModel> getOntology(int type, boolean isAdmin) {
		// TODO Auto-generated method stub
		
		List<OntologyModel> list = sqlMapClientTemplate.queryForList("ontology.ontologyList", type); 
		
		for(OntologyModel om : list){
			om.setCount(String.valueOf(getOntologyCount(om.getOntologyID(), type, isAdmin)));
		}
		
		return list;
	}

	@Override
	public int getOntologyCount(String ontologyID, int type, boolean isAdmin) {
		// TODO Auto-generated method stub
		if(type == 0){
			return Integer.parseInt(sqlMapClientTemplate.queryForObject("ontology.pipelineOntologyCount", ontologyID).toString());
		}else{
			
			if(isAdmin){
				return Integer.parseInt(sqlMapClientTemplate.queryForObject("ontology.moduleOntologyCountWithAdmin", ontologyID).toString());
			}else{
				return Integer.parseInt(sqlMapClientTemplate.queryForObject("ontology.moduleOntologyCountWithClient", ontologyID).toString());
			}
			
		}
	}

	@Override
	public void deleteOntology(List<String> ontologyList) {
		// TODO Auto-generated method stub
		for(String id : ontologyList){
			sqlMapClientTemplate.delete("ontology.deleteOntology", id);
		}
	}
}
