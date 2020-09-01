package org.kobic.gwt.smart.closha.client.pipeline.composition.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.pipeline.composition.presenter.PipelineCompositionPresenter;

import com.smartgwt.client.widgets.layout.HLayout;

public class PipelineCompositionViewer extends HLayoutWidget implements
		PipelineCompositionPresenter.Display {
	
	public PipelineCompositionViewer(){
	}
	
	@Override
	public HLayout asWidget(){
		return this;
	}
}
