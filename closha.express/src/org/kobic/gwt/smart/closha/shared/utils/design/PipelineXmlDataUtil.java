package org.kobic.gwt.smart.closha.shared.utils.design;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.PointsModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;

public class PipelineXmlDataUtil {
	
	private Element rootElement; 
	private Element desciptionElement;
	private Element modulesElement;	
	private Element moduleElement;
	private Element connectionsElement;
	private Element parametersElement;
	
	private Document doc;
	
	private void setConnectEelements(String[] links, String key){
		if(!links[0].equals("-1") && !links[1].equals("-1")){
			Element connectionElement = new Element("connection");
			
			String connectionUID = CommonUtils.getUUID();
			connectionElement.setAttribute("id", connectionUID);
			connectionElement.setAttribute("key", key);
			connectionElement.setAttribute("source", links[0]);
			connectionElement.setAttribute("target", links[1]);
			connectionElement.setAttribute("source_id", CommonUtils.getUUID());
			connectionElement.setAttribute("target_id", CommonUtils.getUUID());
			
			connectionsElement.addContent(connectionElement);
		}
	}
	
	private void setParameterEelements(String[] parameters, String key, String module){
		Element parameterElement = new Element("parameter");
		
		String parameterUID = CommonUtils.getUUID();
		
		parameterElement.setAttribute("id", parameterUID);
		parameterElement.setAttribute("module", module);
		parameterElement.setAttribute("key", key);
		parameterElement.setAttribute("name", parameters[0].split("[:]")[0]);
		parameterElement.setAttribute("value", parameters[1]);
		
		if(parameters[0].split("[:]")[2].equals("combo")){
			parameterElement.setAttribute("setup_value", parameters[1].split(",")[0]);
		}else{
			parameterElement.setAttribute("setup_value", parameters[1]);
		}
		
		parameterElement.setAttribute("description", parameters[0].split("[:]")[1]);
		parameterElement.setAttribute("type", parameters[0].split("[:]")[2]);
		parameterElement.setAttribute("parameter_type", parameters[0].split("[:]")[3]);
		parameterElement.setAttribute("extensions", parameters[0].split("[:]")[4]);
		
		parametersElement.addContent(parameterElement);
	}
	
	public void createWorkFlowXMLFromData(String name, String userID, 
			String version, String description, String readName, String writeName){
		
		rootElement = new Element("closha");
		rootElement.setAttribute("id", CommonUtils.getUUID());
		rootElement.setAttribute("name", name);
		rootElement.setAttribute("author", userID);
		rootElement.setAttribute("version", version);
		
		desciptionElement = new Element("description");
		desciptionElement.addContent(description);
		
		modulesElement = new Element("modules");
		connectionsElement = new Element("connections");
		parametersElement = new Element("parameters");
		
		rootElement.addContent(desciptionElement);
		rootElement.addContent(modulesElement);
		rootElement.addContent(connectionsElement);
		rootElement.addContent(parametersElement);
	
		String path = PipelineXmlDataUtil.class.getResource(readName).getPath();
		String xmlPath = PipelineXmlDataUtil.class.getResource("/data/register/xml").getPath() + File.separator + writeName;

		File file = new File(path);

		try {
			String key = "";
			String module = "";
			List<String> lines = FileUtils.readLines(file);
			String connections[] = new String[2];
			String connectionsStore[] = null;
			String parameters[] = new String[3];
			String parametersStore[] = null;
			
			for(String line : lines){
				String[] tabs = line.split("\t");
				
				key = tabs[6];
				module = tabs[0];
				
				moduleElement = new Element("module");
				moduleElement.setAttribute("type", "module");
				moduleElement.setAttribute("id", CommonUtils.getUUID());
				moduleElement.setAttribute("key", key);
				moduleElement.setAttribute("name", module);
				moduleElement.setAttribute("img_src", tabs[1]);
				moduleElement.setAttribute("img_width", tabs[2]);
				moduleElement.setAttribute("img_height", tabs[3]);
				moduleElement.setAttribute("x", tabs[4]);
				moduleElement.setAttribute("y", tabs[5]);
				moduleElement.setAttribute("parameter_number", tabs[7]);
				
				if(tabs[8].matches(".*&.*")){
					connectionsStore = new String[tabs[8].split("&").length];
					connectionsStore = tabs[8].split("&");
					for(String temp : connectionsStore){
						connections[0] = temp.split("[|]")[0];
						connections[1] = temp.split("[|]")[1];
						setConnectEelements(connections, key);
					}
				}else{
					connections[0] = tabs[8].split("[|]")[0];
					connections[1] = tabs[8].split("[|]")[1];
					setConnectEelements(connections, key);
				}
				
				moduleElement.setAttribute("status", tabs[9]);
				
				if(tabs[10].split("[|]").length != 0){
					if(tabs[10].matches(".*&.*")){
						parametersStore = new String[tabs[10].split("&").length];
						parametersStore = tabs[10].split("&");
						for(String temp : parametersStore){
							parameters = temp.split("[|]");
							setParameterEelements(parameters, key, module);
						}
					}else{
						parameters = tabs[10].split("[|]");
						setParameterEelements(parameters, key, module);
					}
				}
				moduleElement.setText(tabs[11]);
				moduleElement.setAttribute("isTerminate", tabs[12]);
				moduleElement.setAttribute("version", tabs[13]);
				moduleElement.setAttribute("script", tabs[14]);
				moduleElement.setAttribute("url", tabs[15]);
				
				modulesElement.addContent(moduleElement);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Document doc = new Document(rootElement);

		XMLOutputter out = new XMLOutputter();
		Format f = out.getFormat();
		f.setEncoding("euc-kr");
		f.setLineSeparator("\n");
		f.setIndent(Constants.SPLIT_TAB);
		f.setTextMode(Format.TextMode.TRIM);
		out.setFormat(f);
		
		try {
			out.output(doc, System.out);
			out.output(doc, new FileWriter(xmlPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public XmlDispatchModel drawParserXML(String instancePipelineXML){
		
		SAXBuilder builder = new SAXBuilder();
		XmlDispatchModel xmlDispatchBean = new XmlDispatchModel();
		
		try{
			doc = builder.build(new StringReader(instancePipelineXML));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element getRootElement = doc.getRootElement();
		xmlDispatchBean.setId(getRootElement.getAttributeValue("id"));
		xmlDispatchBean.setName(getRootElement.getAttributeValue("name"));
		xmlDispatchBean.setAuthor(getRootElement.getAttributeValue("author"));
		xmlDispatchBean.setVersion(getRootElement.getAttributeValue("version"));
		
		Element descElement = getRootElement.getChild("description");
		xmlDispatchBean.setDescription(descElement.getText());
		
		
		//module value setting
		Element getModulesElement = getRootElement.getChild("modules");
		List<Element> getModuleElement = getModulesElement.getChildren("module");

		List<XmlModuleModel> xmlModulesBeanList = new ArrayList<XmlModuleModel>(getModuleElement.size());
		
		for(Element moduleElement : getModuleElement){
			XmlModuleModel xmlModuleModel = new XmlModuleModel();
			xmlModuleModel.setType(moduleElement.getAttributeValue("type"));
			xmlModuleModel.setModuleID(moduleElement.getAttributeValue("id"));
			xmlModuleModel.setKey(moduleElement.getAttributeValue("key"));
			xmlModuleModel.setModuleName(moduleElement.getAttributeValue("name"));
			xmlModuleModel.setImgSrc(moduleElement.getAttributeValue("img_src"));
			xmlModuleModel.setImgWidth(Integer.parseInt(moduleElement.getAttributeValue("img_width")));
			xmlModuleModel.setImgHeight(Integer.parseInt(moduleElement.getAttributeValue("img_height")));
			xmlModuleModel.setX(Integer.parseInt(moduleElement.getAttributeValue("x")));
			xmlModuleModel.setY(Integer.parseInt(moduleElement.getAttributeValue("y")));
			xmlModuleModel.setParameterNumber(moduleElement.getAttributeValue("parameter_number"));
			xmlModuleModel.setStatus(moduleElement.getAttributeValue("status"));
			xmlModuleModel.setModuleDesc(moduleElement.getText());
			xmlModuleModel.setUrl(moduleElement.getAttributeValue("url"));
			xmlModuleModel.setOpen(Boolean.parseBoolean(moduleElement.getAttributeValue("is_open")));
			xmlModuleModel.setMulti(Boolean.parseBoolean(moduleElement.getAttributeValue("is_multi")));
			xmlModuleModel.setAdmin(Boolean.parseBoolean(moduleElement.getAttributeValue("is_admin")));
			xmlModuleModel.setCheck(Boolean.parseBoolean(moduleElement.getAttributeValue("is_check")));
			xmlModuleModel.setParallel(Boolean.parseBoolean(moduleElement.getAttributeValue("is_parallel")));
			xmlModuleModel.setAlignment(Boolean.parseBoolean(moduleElement.getAttributeValue("is_alignment")));
			
			switch (Integer.parseInt(moduleElement.getAttributeValue("isTerminate"))){
				case 1 :
					xmlModuleModel.setTerminate(false);
				case 0 :
					xmlModuleModel.setTerminate(true);
			}
			xmlModuleModel.setModuleAuthor(moduleElement.getAttributeValue("author"));
			xmlModuleModel.setVersion(moduleElement.getAttributeValue("version"));
			xmlModuleModel.setAppFormat(moduleElement.getAttributeValue("app_format"));
			
			if(xmlModuleModel.getAppFormat().equals(Constants.SCRIPT_TYPE)){
				xmlModuleModel.setScriptPath(moduleElement.getAttributeValue("script"));
				xmlModuleModel.setLanguage(moduleElement.getAttributeValue("language"));
			}else{
				xmlModuleModel.setCmd(moduleElement.getAttributeValue("cmd"));
			}
			
			xmlModulesBeanList.add(xmlModuleModel);
		}

		//connection value setting
		Element getConnectionsElement = getRootElement.getChild("connections");
		List<Element> getConnectionElement = getConnectionsElement.getChildren("connection");
		
		List<XmlConnectLinkedModel> connectionsBeanList = new ArrayList<XmlConnectLinkedModel>(getConnectionElement.size());
		List<PointsModel> pList;
		for(Element connectionElement : getConnectionElement){
			XmlConnectLinkedModel xmlConnectionModel = new XmlConnectLinkedModel();
			xmlConnectionModel.setId(connectionElement.getAttributeValue("id"));
			xmlConnectionModel.setKey(connectionElement.getAttributeValue("key"));
			xmlConnectionModel.setSourceId(connectionElement.getAttributeValue("source_id"));
			xmlConnectionModel.setSource(connectionElement.getAttributeValue("source"));
			xmlConnectionModel.setTargetId(connectionElement.getAttributeValue("target_id"));
			xmlConnectionModel.setTarget(connectionElement.getAttributeValue("target"));
			
			List<Element> getPointElement = connectionElement.getChildren("point");
			pList = new ArrayList<PointsModel>();
			
			for(Element pointElement : getPointElement){
				PointsModel pm = new PointsModel();
				pm.setLeft(Integer.parseInt(pointElement.getAttributeValue("left")));
				pm.setTop(Integer.parseInt(pointElement.getAttributeValue("top")));
				pList.add(pm);
			}
			
			xmlConnectionModel.setPoints(pList);
			
			connectionsBeanList.add(xmlConnectionModel);
		}
		
		//parameter value setting
		
		Element getParametersElement = getRootElement.getChild("parameters");
		List<Element> getParameterElement = getParametersElement.getChildren("parameter");
		
		List<XmlParameterModel> parametersBeanList = new ArrayList<XmlParameterModel>(getParameterElement.size());
		
		for(Element parameterElement : getParameterElement){
			XmlParameterModel xmlParameterModel = new XmlParameterModel();
			xmlParameterModel.setId(parameterElement.getAttributeValue("id"));
			xmlParameterModel.setKey(parameterElement.getAttributeValue("key"));
			xmlParameterModel.setName(parameterElement.getAttributeValue("name"));
			xmlParameterModel.setValue(parameterElement.getAttributeValue("value"));
			xmlParameterModel.setSetupValue(parameterElement.getAttributeValue("setup_value"));
			xmlParameterModel.setDescription(parameterElement.getAttributeValue("description"));
			xmlParameterModel.setType(parameterElement.getAttributeValue("type"));
			xmlParameterModel.setModule(parameterElement.getAttributeValue("module"));
			xmlParameterModel.setParameterType(parameterElement.getAttributeValue("parameter_type"));	
			xmlParameterModel.setExtensions(parameterElement.getAttributeValue("extensions"));
			
			parametersBeanList.add(xmlParameterModel);
		}
		
		xmlDispatchBean.setModulesBeanList(xmlModulesBeanList);
		xmlDispatchBean.setConnectionsBeanList(connectionsBeanList);
		xmlDispatchBean.setParametersBeanList(parametersBeanList);
		
		return xmlDispatchBean;
	}
	
	public String updateWorkFlowXML(XmlDispatchModel xmlDispatchModel){
		
		String xml = "";
		
		rootElement = new Element("closha");
		rootElement.setAttribute("id", xmlDispatchModel.getId());
		rootElement.setAttribute("name", xmlDispatchModel.getName());
		rootElement.setAttribute("author", xmlDispatchModel.getAuthor());
		rootElement.setAttribute("version", xmlDispatchModel.getVersion());
		
		desciptionElement = new Element("description");
		desciptionElement.addContent(xmlDispatchModel.getDescription());
		
		modulesElement = new Element("modules");
		connectionsElement = new Element("connections");
		parametersElement = new Element("parameters");
		
		rootElement.addContent(desciptionElement);
		rootElement.addContent(modulesElement);
		rootElement.addContent(connectionsElement);
		rootElement.addContent(parametersElement);
		
		for(XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()){
			
			moduleElement = new Element("module");
			moduleElement.setAttribute("type", xm.getType());
			moduleElement.setAttribute("id", xm.getModuleID());
			moduleElement.setAttribute("key", xm.getKey());
			moduleElement.setAttribute("name", xm.getModuleName());
			moduleElement.setAttribute("img_src", xm.getImgSrc());
			moduleElement.setAttribute("img_width", String.valueOf(xm.getImgWidth()));
			moduleElement.setAttribute("img_height", String.valueOf(xm.getImgHeight()));
			moduleElement.setAttribute("x", String.valueOf(xm.getX()));
			moduleElement.setAttribute("y", String.valueOf(xm.getY()));
			moduleElement.setAttribute("parameter_number", xm.getParameterNumber());
			moduleElement.setAttribute("status", xm.getStatus());
			if(xm.isTerminate()){
				moduleElement.setAttribute("isTerminate", "0");
			}else{
				moduleElement.setAttribute("isTerminate", "1");
			}
			moduleElement.setAttribute("author", xm.getModuleAuthor());
			moduleElement.setAttribute("version", xm.getVersion());
			moduleElement.setAttribute("app_format", xm.getAppFormat());
			
			if(xm.getAppFormat().equals(Constants.SCRIPT_TYPE)){
				moduleElement.setAttribute("script", xm.getScriptPath());
				moduleElement.setAttribute("language", xm.getLanguage());
			}else{
				moduleElement.setAttribute("cmd", xm.getCmd());
			}
			
			moduleElement.setAttribute("url", xm.getUrl());
			
			moduleElement.setAttribute("is_open", String.valueOf(xm.isOpen()));
			moduleElement.setAttribute("is_multi", String.valueOf(xm.isMulti()));
			moduleElement.setAttribute("is_admin", String.valueOf(xm.isAdmin()));
			moduleElement.setAttribute("is_check", String.valueOf(xm.isCheck()));
			moduleElement.setAttribute("is_parallel", String.valueOf(xm.isParallel()));
			moduleElement.setAttribute("is_alignment", String.valueOf(xm.isAlignment()));
			
			moduleElement.setText(xm.getModuleDesc());
			modulesElement.addContent(moduleElement);
		}
		
		for(XmlConnectLinkedModel xc : xmlDispatchModel.getConnectionsBeanList()){
			Element connectionElement = new Element("connection");
			connectionElement.setAttribute("id", xc.getId());
			connectionElement.setAttribute("key", xc.getKey());
			connectionElement.setAttribute("source", xc.getSource());
			connectionElement.setAttribute("target", xc.getTarget());
			connectionElement.setAttribute("source_id", xc.getSourceId());
			connectionElement.setAttribute("target_id", xc.getTargetId());
			
			for(PointsModel pm : xc.getPoints()){
				Element pointElement = new Element("point");
				pointElement.setAttribute("left", String.valueOf(pm.getLeft()));
				pointElement.setAttribute("top", String.valueOf(pm.getTop()));
				connectionElement.addContent(pointElement);
			}
			
			connectionsElement.addContent(connectionElement);
		}
		
		for(XmlParameterModel xp : xmlDispatchModel.getParametersBeanList()){
			
			Element parameterElement = new Element("parameter");
			parameterElement.setAttribute("id", xp.getId());
			parameterElement.setAttribute("module", xp.getModule());
			parameterElement.setAttribute("key", xp.getKey());
			parameterElement.setAttribute("name", xp.getName());
			parameterElement.setAttribute("value", xp.getValue());
			parameterElement.setAttribute("setup_value", xp.getSetupValue());
			parameterElement.setAttribute("description", xp.getDescription());
			parameterElement.setAttribute("type", xp.getType());
			parameterElement.setAttribute("parameter_type", xp.getParameterType());
			parameterElement.setAttribute("extensions", xp.getExtensions());
			
			parametersElement.addContent(parameterElement);
		}
		
		//write XML format
		Document doc = new Document(rootElement);

		XMLOutputter out = new XMLOutputter();
		Format f = out.getFormat();
		f.setEncoding("euc-kr");
		f.setLineSeparator("\n");
		f.setIndent(Constants.SPLIT_TAB);
		f.setTextMode(Format.TextMode.TRIM);
		out.setFormat(f);
		
		try {
			xml = out.outputString(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return xml;
	}
}