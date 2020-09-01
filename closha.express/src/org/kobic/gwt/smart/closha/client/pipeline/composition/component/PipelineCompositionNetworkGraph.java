package org.kobic.gwt.smart.closha.client.pipeline.composition.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.function.SelectModuleEvent;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.PipelineModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.chap.links.client.Network;
import com.chap.links.client.events.SelectHandler;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.TimeOfDay;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.smartgwt.client.widgets.layout.VLayout;

public class PipelineCompositionNetworkGraph {

	private HandlerManager eventBus;
	private VLayout pane;
	private Network network;
	
	private static int LENGTH_MODULE = 50;

	private int index;
		
	private Map<String, PipelineModuleModel> nodeMap;
	
	public PipelineCompositionNetworkGraph(HandlerManager eventBus,
			VLayout pane, XmlDispatchModel xmlDispatchModel) {
		this.eventBus = eventBus;
		this.pane = pane;
		
		nodeMap = new HashMap<String, PipelineModuleModel>();
		index = 0;
		
		drawGraph(xmlDispatchModel);
	}

	private void drawGraph(final XmlDispatchModel data) {
		final Runnable onLoadCallback = new Runnable() {
			public void run() {

				// Create nodes table with some data

				DataTable nodes = DataTable.create();

				nodes.addColumn(DataTable.ColumnType.STRING, "id");
				nodes.addColumn(DataTable.ColumnType.STRING, "text");
				nodes.addColumn(DataTable.ColumnType.STRING, "image");
				nodes.addColumn(DataTable.ColumnType.STRING, "style");
				nodes.addColumn(DataTable.ColumnType.STRING, "type");

				DataTable links = DataTable.create();
				links.addColumn(DataTable.ColumnType.STRING, "from");
				links.addColumn(DataTable.ColumnType.STRING, "to");
				links.addColumn(DataTable.ColumnType.NUMBER, "length");

				// data binding
				String id = data.getId();
				
				// 1. root
				addRow(nodes, id, data.getName(), "images/closha/icon/circle_1.png", "image", "");
				PipelineModuleModel rootNode = new PipelineModuleModel(
						data.getName(), data.getDescription(),
						data.getAuthor(), null, null, data.getVersion(), null,
						data.getId());
				nodeMap.put(CommonUtilsGwt.changeType(index++), rootNode);
				
				// 2. pipeline module
				for (XmlModuleModel mm : data.getModulesBeanList()) {
					
					if (!mm.getModuleName().toLowerCase().equals(Constants.MODULE_START)
							&& !mm.getModuleName().toLowerCase().equals(Constants.MODULE_END)) {

						addRow(nodes, mm.getModuleID(), mm.getModuleName(),
								"images/closha/icon/circle_2.png", "image",
								"module");
						addRow(links, id, mm.getModuleID(), LENGTH_MODULE);
						
						PipelineModuleModel moduleNode = new PipelineModuleModel(
								mm.getModuleName(), mm.getModuleDesc(),
								mm.getVersion(), null, null, mm.getVersion(), null,
								mm.getModuleID());
						nodeMap.put(CommonUtilsGwt.changeType(index++), moduleNode);
					}
				}

				// Create options
				Network.Options options = Network.Options.create();
				
				if(pane.getWidth() < 250){
					options.setWidth(String.valueOf(pane.getWidth()
							+ (250 - pane.getWidth()))
							+ Constants.PX);
				}else{
					options.setWidth(pane.getWidthAsString() + Constants.PX);
				}
				
				if(pane.getHeight() < 250){
					options.setHeight(String.valueOf(pane.getHeight()
							+ (250 - pane.getHeight()))
							+ Constants.PX);
				}else{
					options.setHeight(pane.getHeightAsString() + Constants.PX);
				}
				
				options.setBorderWidth(0);
				options.setBorderColor("#FFFFFF");
				options.setLinksWidth(1);
				options.setLinksColor("#E19D54");
				options.setNodesFontSize("12");
				options.setGroupHighlightColor(id, "#eeeeee");

				// create the visualization, with data and options
				network = new Network(nodes, links, options);
				network.addSelectHandler(createSelectHandler(network));
				network.animationStop();
				pane.addMember(network);
				pane.setWidth(pane.getWidth() + 1);
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallback);
	}

	private SelectHandler createSelectHandler(final Network network) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				JsArray<Selection> sel = network.getSelections();

				for (int i = 0; i < sel.length(); i++) {
					int row = sel.get(i).getRow();
					PipelineModuleModel linkedNetworkNodeModel = nodeMap.get(CommonUtilsGwt.changeType(row));
					eventBus.fireEvent(new SelectModuleEvent(linkedNetworkNodeModel));
				}
			}
		};
	}

	private void addRow(DataTable table, Object... fields) {
		int i = table.getNumberOfRows();
		table.addRow();

		int col = 0;
		for (Object field : fields) {
			if (field instanceof Boolean)
				table.setValue(i, col, (Boolean) field);
			else if (field instanceof String)
				table.setValue(i, col, (String) field);
			else if (field instanceof Double)
				table.setValue(i, col, (Double) field);
			else if (field instanceof Integer)
				table.setValue(i, col, (Integer) field);
			else if (field instanceof Date)
				table.setValue(i, col, (Date) field);
			else if (field instanceof TimeOfDay)
				table.setValue(i, col, (TimeOfDay) field);

			col++;
		}
	}
}