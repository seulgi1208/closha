package org.kobic.gwt.smart.closha.shared.model.pipeline.register;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterPipelineNetworkGraphModel implements IsSerializable{
	
	private List<OntologyModel> ontology;
	private List<PipelineModel> registerPipeline;
	private List<RegisterModuleModel> registerPipelineModule;
	
	public List<OntologyModel> getOntology() {
		return ontology;
	}
	public void setOntology(List<OntologyModel> ontology) {
		this.ontology = ontology;
	}
	public List<PipelineModel> getRegisterPipeline() {
		return registerPipeline;
	}
	public void setRegisterPipeline(List<PipelineModel> registerPipeline) {
		this.registerPipeline = registerPipeline;
	}
	public List<RegisterModuleModel> getRegisterPipelineModule() {
		return registerPipelineModule;
	}
	public void setRegisterPipelineModule(
			List<RegisterModuleModel> registerPipelineModule) {
		this.registerPipelineModule = registerPipelineModule;
	}

}
