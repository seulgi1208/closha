package org.kobic.gwt.smart.closha.client.section.job.history.component;

import org.kobic.gwt.smart.closha.client.file.explorer.event.ShowFileBrowserEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class JobHistoryListGrid extends ListGrid {
	
	private final HandlerManager eventBus;

	public JobHistoryListGrid(HandlerManager eventBus) {
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setShowRollOver(false);
		setShowSelectedStyle(false);
		setCanReorderRecords(true);
		setShowRecordComponents(true);          
		setShowRecordComponentsByCell(true);  
		setShowHeader(true);
		setCellHeight(32);
		setEmptyMessage("There are no data.");
		
		this.eventBus = eventBus;
	}
	
	@Override
	protected Canvas createRecordComponent(final ListGridRecord record,
			Integer colNum) {

		String fieldName = this.getFieldName(colNum);
		
		final String projectPath = record.getAttributeAsString("projectPath"); 

		if(fieldName.equals("report")){
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(30);
			recordCanvas.setWidth100();
			recordCanvas.setAlign(Alignment.CENTER);
			
			ImgButton resultConnectionButton = new ImgButton();
			resultConnectionButton.setShowDown(false);
			resultConnectionButton.setShowRollOver(false);
			resultConnectionButton.setLayoutAlign(Alignment.CENTER);
			resultConnectionButton.setSrc("closha/icon/folder_page.png");
			resultConnectionButton.setPrompt("Go to the result path.");
			resultConnectionButton.setHeight(16);
			resultConnectionButton.setWidth(16);

			resultConnectionButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
					eventBus.fireEvent(new ShowFileBrowserEvent(true, projectPath));
//					eventBus.fireEvent(new FileBrowserDataLoadEvent(projectPath));
				}
			});
			
			recordCanvas.addMember(resultConnectionButton);
			
			return recordCanvas;
		}else{
			return null;
		}
	}
	
	@Override
	protected Canvas getExpansionComponent(final ListGridRecord record) {
		
		VLayout layout = new VLayout(5);
		layout.setPadding(5);

		String instanceDesc = record.getAttributeAsString("instanceDesc");

		HTMLPane htmlPane = new HTMLPane();
		htmlPane.setContents(instanceDesc);
		htmlPane.setHeight(300);
		htmlPane.setWidth100();

		layout.addMember(htmlPane);

		return layout;
	}
}