package org.kobic.gwt.smart.closha.client.board.viewer;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.board.presenter.BoardPresenter;
import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class BoardViewer extends VLayoutWidget implements BoardPresenter.Display{
	
	private IButton fButton;
	private IButton iButton;
	private IButton lButton;
	private IButton sButton;
	private IButton writeButton;
	private IButton nextButton;
	private IButton prevButton;
	private IButton skipGoButton;
	private IButton skipBackButton;
	
	private SelectItem numItem;
	private SelectItem issueItem;
	private SelectItem searchItem;
	
	private ListGrid listGrid;
	
	private DynamicForm form_1;
	private DynamicForm form_2;
	private DynamicForm form_3;
	
	private TextItem searchText;
	
	private Label totalCountLabel;
	private Label countLabel;
	private Label splitMarker;
	
	public BoardViewer(){
		
		setMargin(5);
		setMembersMargin(10);
		
		HLayout row_1 = new HLayout();
		row_1.setWidth100();
		row_1.setMembersMargin(10);
		row_1.setAlign(Alignment.LEFT); 
		
		fButton = new IButton("All List");
		fButton.setIcon("closha/icon/application_view_list.png");
		
		iButton = new IButton("My writing list");
		iButton.setIcon("closha/icon/user_edit.gif");
		
		form_1 = new DynamicForm();  
		form_1.setWidth(180);
        
		numItem = new SelectItem();
		numItem.setWidth(70);
		numItem.setDefaultValues("10");
		numItem.setTitle("Count Of List");

		LinkedHashMap<String, String> valueMap_1 = new LinkedHashMap<String, String>();
		valueMap_1.put("10", "<b>10</b>");
		valueMap_1.put("20", "<b>20</b>");
		valueMap_1.put("30", "<b>30</b>");
		
		numItem.setValueMap(valueMap_1);
		
		LinkedHashMap<String, String> valueIcons_1 = new LinkedHashMap<String, String>();
		valueIcons_1.put("10", "closha/icon/document-text.png");
		valueIcons_1.put("20", "closha/icon/document-text.png");
		valueIcons_1.put("30", "closha/icon/document-text.png");
		
		numItem.setValueIcons(valueIcons_1);
		
		form_1.setItems(numItem);

		DynamicForm issueForm = new DynamicForm();
		issueForm.setWidth(200);
        
		issueItem = new SelectItem();		
		issueItem.setWidth(120);
		issueItem.setDefaultValues("comment");
		issueItem.setTitle("Type");
		issueItem.setName("type");
		
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put(Constants.DEBUG, "<b>Debug</b>");
		valueMap.put(Constants.COMMENT, "<b>Notice</b>");
		valueMap.put(Constants.ISSUE, "<b>Issue</b>");
		
		issueItem.setValueMap(valueMap);
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put(Constants.DEBUG, Constants.DEBUG_IMG);
		valueIcons.put(Constants.COMMENT, Constants.COMMENT_IMG);
		valueIcons.put(Constants.ISSUE, Constants.ISSUE_IMG);
		
		issueItem.setValueIcons(valueIcons);
		
		issueForm.setItems(issueItem);
		
		skipGoButton = new IButton();
		skipGoButton.setWidth(25);
		skipGoButton.setIcon("closha/icon/control_skip.png");
		
		prevButton = new IButton();
		prevButton.setWidth(25);
		prevButton.setIcon("closha/icon/arrow_skip_180.png");
		
		countLabel = new Label(Constants.INIT_COUNT);
		countLabel.setWidth(5);
		
		splitMarker = new Label(Constants.SPLASH);
		splitMarker.setWidth(5);
		
		totalCountLabel = new Label(Constants.INIT_COUNT);
		totalCountLabel.setWidth(5);
		
		nextButton = new IButton();
		nextButton.setWidth(25);
		nextButton.setIcon("closha/icon/arrow_skip.png");
		
		skipBackButton = new IButton();
		skipBackButton.setWidth(25);
		skipBackButton.setIcon("closha/icon/control_skip_180.png");
		
		row_1.addMember(fButton);
		row_1.addMember(iButton);
		row_1.addMember(issueForm);
		row_1.addMember(form_1);
		row_1.addMember(skipBackButton);
		row_1.addMember(prevButton);
		row_1.addMember(countLabel);
		row_1.addMember(splitMarker);
		row_1.addMember(totalCountLabel);
		row_1.addMember(nextButton);
		row_1.addMember(skipGoButton);
		
		SectionStack sectionStack = new SectionStack();  
        sectionStack.setWidth100();
        sectionStack.setHeight100();  
		
		String title = Canvas.imgHTML("closha/icon/paste_plain.png") + "&nbsp;Board";  
        
		SectionStackSection section = new SectionStackSection(title);
        section.setCanCollapse(false);  
        section.setExpanded(true);
		
		listGrid = new ListGrid() {  
			@Override  
		    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				/*"font-weight:bold; color:#d64949; font-family:Verdana,Bitstream Vera Sans,sans-serif; font-size:11px;";*/
				return "font-size:11px; line-height: 20px";
		    } 
        };  
  
		listGrid.setWidth100();
		listGrid.setHeight100();
		listGrid.setShowAllRecords(true);  
		listGrid.setShowRowNumbers(true);  
		listGrid.setWrapCells(true);  
        listGrid.setFixedRecordHeights(false);  
        listGrid.setEmptyMessage("There are no data.");
		
		ListGridField typeField = new ListGridField("type", "Type", 100);  
		typeField.setAlign(Alignment.CENTER);  
		typeField.setType(ListGridFieldType.IMAGE); 
        
		ListGridField summaryField = new ListGridField("summary", "Summary");
		summaryField.setAlign(Alignment.CENTER);  
		
		ListGridField writer = new ListGridField("writer", "Writer", 100);
		writer.setAlign(Alignment.CENTER);
		
		ListGridField hitCountField = new ListGridField("hitCount", "Hit Count", 50);
		hitCountField.setAlign(Alignment.CENTER);
		
		ListGridField recommandCountField = new ListGridField("recommandCount", "Comment", 100);
		recommandCountField.setAlign(Alignment.CENTER);
		
		ListGridField dateField = new ListGridField("date", "Date", 250);
		dateField.setAlign(Alignment.CENTER);
		
		listGrid.setFields(typeField, summaryField, writer, hitCountField, recommandCountField, dateField);
		
		section.setItems(listGrid);
		sectionStack.setSections(section);
		
		HLayout row_2 = new HLayout();
		row_2.setWidth100();
		row_2.setMembersMargin(10);
		row_2.setAlign(Alignment.CENTER); 
		
		lButton = new IButton("List");
		lButton.setIcon("closha/icon/application_view_list.png");
		
		form_2 = new DynamicForm();  
		
		searchItem = new SelectItem();
		searchItem.setDefaultValues("summary");
		searchItem.setShowTitle(false);

		LinkedHashMap<String, String> valueMap_2 = new LinkedHashMap<String, String>();
		valueMap_2.put("summary", "<b>Title</b>");
		valueMap_2.put("content", "<b>Content</b>");
		valueMap_2.put("writer", "<b>Writer</b>");
		
		LinkedHashMap<String, String> valueIcons_2 = new LinkedHashMap<String, String>();
		valueIcons_2.put("summary", "closha/icon/tick.png");
		valueIcons_2.put("content", "closha/icon/tick.png");
		valueIcons_2.put("writer", "closha/icon/tick.png");
		
		searchItem.setValueMap(valueMap_2);
		searchItem.setValueIcons(valueIcons_2);
		
		form_2.setItems(searchItem);
		
		form_3 = new DynamicForm();  
		
		searchText = new TextItem();
		searchText.setWidth(250);
		searchText.setShowTitle(false);
		
		form_3.setItems(searchText);
		
		sButton = new IButton("Search");
		sButton.setIcon("closha/icon/zoom.png");
		
		writeButton = new IButton("Write");
		writeButton.setIcon("closha/icon/pencil.png");
		
		row_2.addMember(lButton);
		row_2.addMember(form_2);
		row_2.addMember(form_3);
		row_2.addMember(sButton);
		row_2.addMember(writeButton);
		
		addMember(row_1);
		addMember(sectionStack);
		addMember(row_2);
	}
	
	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IButton getWriteButton() {
		// TODO Auto-generated method stub
		return writeButton;
	}

	@Override
	public ListGrid getListGrid() {
		// TODO Auto-generated method stub
		return listGrid;
	}

	@Override
	public SelectItem getNumItem() {
		// TODO Auto-generated method stub
		return numItem;
	}

	@Override
	public IButton getMyButton() {
		// TODO Auto-generated method stub
		return iButton;
	}

	@Override
	public IButton getListButton() {
		// TODO Auto-generated method stub
		return lButton;
	}

	@Override
	public IButton getFullButton() {
		// TODO Auto-generated method stub
		return fButton;
	}

	@Override
	public IButton getSearchButton() {
		// TODO Auto-generated method stub
		return sButton;
	}

	@Override
	public SelectItem getSearchItem() {
		// TODO Auto-generated method stub
		return searchItem;
	}

	@Override
	public TextItem getSearchTextItem() {
		// TODO Auto-generated method stub
		return searchText;
	}

	@Override
	public SelectItem getIssueItem() {
		// TODO Auto-generated method stub
		return issueItem;
	}

	@Override
	public IButton getNextButton() {
		// TODO Auto-generated method stub
		return nextButton;
	}

	@Override
	public IButton getprevButton() {
		// TODO Auto-generated method stub
		return prevButton;
	}

	@Override
	public IButton getSkipGoButton() {
		// TODO Auto-generated method stub
		return skipGoButton;
	}

	@Override
	public IButton getSkipBackButton() {
		// TODO Auto-generated method stub
		return skipBackButton;
	}

	@Override
	public Label getTotalCountLabel() {
		// TODO Auto-generated method stub
		return totalCountLabel;
	}

	@Override
	public Label getCountLabel() {
		// TODO Auto-generated method stub
		return countLabel;
	}
}