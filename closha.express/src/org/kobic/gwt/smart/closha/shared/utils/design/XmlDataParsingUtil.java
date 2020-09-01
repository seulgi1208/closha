package org.kobic.gwt.smart.closha.shared.utils.design;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.IntegrationModuleConnectModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleInfomModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleConnectionInfomModel;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class XmlDataParsingUtil {
	
	public static IntegrationModuleConnectModel getIntegrationLinkedModel(String drawPipelineXml){
		
		IntegrationModuleConnectModel integrationLinkedModel = new IntegrationModuleConnectModel();
		integrationLinkedModel.setModuleFunctionModel(getParserLinkedXmlFunctionData(drawPipelineXml));
		integrationLinkedModel.setModuleLinksModel(getParserLinkedXmlLinksData(drawPipelineXml));
		
		return integrationLinkedModel;
	}
	
	public static List<ModuleInfomModel> getParserLinkedXmlFunctionData(String drawPipelineXml){
		Document doc = XMLParser.parse(drawPipelineXml);
		Element root = doc.getDocumentElement();
		
		List<ModuleInfomModel> list = new ArrayList<ModuleInfomModel>();
		
		
		NodeList nodeList = root.getElementsByTagName("function");
		
		for(int i = 0; i < nodeList.getLength(); i++){
			
			Element functionEelements = (Element) nodeList.item(i);
			
			ModuleInfomModel moduleFunctionModel = new ModuleInfomModel();
			moduleFunctionModel.setLeft(functionEelements.getAttribute("left"));
			moduleFunctionModel.setTop(functionEelements.getAttribute("top"));
			moduleFunctionModel.setId(functionEelements.getAttribute("id"));
			moduleFunctionModel.setIdentifier(functionEelements.getAttribute("identifier"));
			if(functionEelements.getFirstChild() != null){
				moduleFunctionModel.setName(functionEelements.getFirstChild().toString());
			}else{
				moduleFunctionModel.setName(null);
			}
			
			list.add(moduleFunctionModel);
		}
		return list;
	}
	
	public static List<ModuleConnectionInfomModel> getParserLinkedXmlLinksData(String drawPipelineXml){
		
		List<ModuleInfomModel> functionList = new ArrayList<ModuleInfomModel>();
		functionList = getParserLinkedXmlFunctionData(drawPipelineXml);
		
		Document doc = XMLParser.parse(drawPipelineXml);
		Element root = doc.getDocumentElement();
		
		List<ModuleConnectionInfomModel> list = new ArrayList<ModuleConnectionInfomModel>();
		
		NodeList nodeList = root.getElementsByTagName("link");
		
		for(int i=0;i<nodeList.getLength();i++){
			Element linkElements = (Element) nodeList.item(i);
			
			ModuleConnectionInfomModel moduleLinksModel = new ModuleConnectionInfomModel();
			moduleLinksModel.setStartID(linkElements.getAttribute("startid"));
			moduleLinksModel.setEndID(linkElements.getAttribute("endid"));
			
			for(ModuleInfomModel moduleFunctionModel : functionList){
				if(moduleLinksModel.getStartID().equals(moduleFunctionModel.getId())){
					moduleLinksModel.setStartKey(moduleFunctionModel.getIdentifier());
					moduleLinksModel.setStartModuleName(moduleFunctionModel.getName());
				}else if(moduleLinksModel.getEndID().equals(moduleFunctionModel.getId())){
					moduleLinksModel.setEndKey(moduleFunctionModel.getIdentifier());
					moduleLinksModel.setEndModuleName(moduleFunctionModel.getName());
				}
			}
			
			NodeList pointNodeList = linkElements.getElementsByTagName("point");
			
			int[][] pointList = new int[pointNodeList.getLength()][2];
			
			for(int j = 0; j < pointNodeList.getLength(); j++){
				Element pointElement = (Element) pointNodeList.item(j);
				int[] point = {Integer.decode(pointElement.getAttribute("x")),
						Integer.decode(pointElement.getAttribute("y"))};
				pointList[j] = point;
			}
			moduleLinksModel.setPoints(pointList);
			list.add(moduleLinksModel);
		}
		return list;
	}
}
