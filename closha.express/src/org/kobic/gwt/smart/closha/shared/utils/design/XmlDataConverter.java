package org.kobic.gwt.smart.closha.shared.utils.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class XmlDataConverter {
	
	public static Map<Integer, XmlModuleModel> convertModulesListToMap(List<XmlModuleModel> modules){
		Map<Integer, XmlModuleModel> moduleMap = new HashMap<Integer, XmlModuleModel>();
		for(XmlModuleModel xm : modules){
			System.out.println(Integer.parseInt(xm.getKey()) + ":" + xm.getModuleName());
			moduleMap.put(Integer.parseInt(xm.getKey()), xm);
		}
		return moduleMap;
	}
	
	public static Map<String, XmlModuleModel> convertModulesList(List<XmlModuleModel> modules){
		Map<String, XmlModuleModel> moduleMap = new HashMap<String, XmlModuleModel>();
		for(XmlModuleModel xm : modules){
			moduleMap.put(xm.getKey(), xm);
		}
		return moduleMap;
	}
	
	public static ListMultimap<Integer, Integer> convertConnectionListToListMultiMap(List<XmlConnectLinkedModel> connections){
		
		ListMultimap<Integer, Integer> multiMap = ArrayListMultimap.create();
		
		for(XmlConnectLinkedModel connection : connections){
			System.out.println(Integer.parseInt(connection.getSource()) + "->" + Integer.parseInt(connection.getTarget()));
			multiMap.put(Integer.parseInt(connection.getSource()), 
					Integer.parseInt(connection.getTarget()));
		}
		return multiMap;
	}
	
	public static ListMultimap<String, XmlParameterModel> convertParameterListToListMultiMap(List<XmlParameterModel> parameters){
		
		ListMultimap<String, XmlParameterModel> multiMap = ArrayListMultimap.create();
		
		for(XmlParameterModel parameter : parameters){
			multiMap.put(parameter.getKey(), parameter);
		}
		
		return multiMap;
	}
	
	public static List<XmlConnectLinkedModel> getConnectionModelData(String source, List<XmlConnectLinkedModel> connectionModelList){
		
		List<XmlConnectLinkedModel> collectList = new ArrayList<XmlConnectLinkedModel>();
		
		for(XmlConnectLinkedModel xc : connectionModelList){
			if(xc.getSource().equals(source)){
				collectList.add(xc);
			}
		}
		return collectList;
	}
	
	public static List<XmlParameterModel> getParametersModelData(Integer key, List<XmlParameterModel> parameters){
		
		List<XmlParameterModel> collectList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xp : parameters){
			if(xp.getKey().equals(String.valueOf(key))){
				collectList.add(xp);
			}
		}
		
		return collectList;
	}
	
	public static List<XmlParameterModel> getParametersModelData(String key, List<XmlParameterModel> parameters){
		
		List<XmlParameterModel> collectList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xp : parameters){
			if(xp.getKey().equals(key)){
				collectList.add(xp);
			}
		}
		
		return collectList;
	}
}
