package org.kobic.gwt.smart.closha.client.monitoring.resource.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.monitoring.resource.presenter.ResourceMonitorPresenter;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.Chart.ZoomType;
import org.moxieapps.gwt.highcharts.client.Legend.Align;
import org.moxieapps.gwt.highcharts.client.Legend.Layout;
import org.moxieapps.gwt.highcharts.client.Legend.VerticalAlign;
import org.moxieapps.gwt.highcharts.client.Series.Type;
import org.moxieapps.gwt.highcharts.client.ToolTip;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ResourceMonitorViewer extends HLayoutWidget implements
		ResourceMonitorPresenter.Display {

	public ResourceMonitorViewer() {

		VLayout pipelineChartLayout = new VLayout();
		pipelineChartLayout.setHeight100();
		pipelineChartLayout.setWidth100();
		
		Chart pipelineChart = new Chart();
		
		pipelineChart.setBackgroundColor("#FFFFFF");
		pipelineChart.setBorderColor("#FFFFFF");
		pipelineChart.setZoomType(ZoomType.X_AND_Y);
		pipelineChart.setType(Type.COLUMN);
		pipelineChart.setChartTitleText("Popular Pipeline");
		
		pipelineChart.getXAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		
		pipelineChart.getYAxis().setMin(0);
		pipelineChart.getYAxis().setAxisTitleText("Usage");
		
		Legend legend = new Legend();
		legend.setLayout(Layout.VERTICAL);
		legend.setBackgroundColor("#FFFFFF");
		legend.setAlign(Align.LEFT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setX(100);
		legend.setY(70);
		legend.setFloating(true);
		legend.setShadow(true);
		
		ToolTip tooltip = new ToolTip();
		tooltip.setFormatter(new ToolTipFormatter() {
			
			@Override
			public String format(ToolTipData toolTipData) {
				// TODO Auto-generated method stub
				return toolTipData.getXAsString() + " : (" + toolTipData.getYAsLong()+")";
			}
		});
		
		pipelineChart.setLegend(legend);
		pipelineChart.setToolTip(tooltip);
		
		final Series pipeline_1 = pipelineChart.createSeries();
		pipeline_1.setName("pipeline_1");
		pipeline_1.setPoints(new Number[] {49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4});		
		pipelineChart.addSeries(pipeline_1);
		
		final Series pipeline_2 = pipelineChart.createSeries();
		pipeline_2.setOption("/series/color", "#000000");
		pipeline_2.setName("pipeline_2");
		pipeline_2.setPoints(new Number[] {83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3});
		pipelineChart.addSeries(pipeline_2);
		
		final Series pipeline_3 = pipelineChart.createSeries();
		pipeline_3.setName("pipeline_3");
		pipeline_3.setPoints(new Number[] {48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2});
		pipelineChart.addSeries(pipeline_3);
		
		final Series pipeline_4 = pipelineChart.createSeries();
		pipeline_4.setOption("/series/color", "#000000");
		pipeline_4.setName("pipeline_4");
		pipeline_4.setPoints(new Number[] {42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1});		
		pipelineChart.addSeries(pipeline_4);
		
		pipelineChartLayout.addMember(pipelineChart);
		
		VLayout userChartLayout = new VLayout();
		userChartLayout.setWidth100();
		userChartLayout.setHeight100();
		
		Chart userChart = new Chart();
		
		userChart.setBackgroundColor("#FFFFFF");
		userChart.setBorderColor("#FFFFFF");
		userChart.setZoomType(ZoomType.X_AND_Y);
		userChart.setType(Type.LINE);
		userChart.setChartTitleText("User Access Count");
		
		userChart.getXAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		
		userChart.getYAxis().setMin(0);
		userChart.getYAxis().setAxisTitleText("Usage");
		
		ToolTip userChartTooltip = new ToolTip();
		userChartTooltip.setFormatter(new ToolTipFormatter() {
			
			@Override
			public String format(ToolTipData toolTipData) {
				// TODO Auto-generated method stub
				return toolTipData.getXAsString() + " : (" + toolTipData.getYAsLong()+")";
			}
		});
		
		userChart.setLegend(legend);
		userChart.setToolTip(userChartTooltip);
		
		final Series user_1 = userChart.createSeries();
		user_1.setName("user_1");
		user_1.setOption("/series/color", "#000000");
		user_1.setPoints(new Number[] {7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6});		
		userChart.addSeries(user_1);
		
		final Series user_2 = userChart.createSeries();
		user_2.setOption("/series/color", "#000000");
		user_2.setName("user_2");
		user_2.setPoints(new Number[] {3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8});
		userChart.addSeries(user_2);
		
		userChartLayout.addMember(userChart);
		
		addMember(pipelineChartLayout);
		addMember(userChartLayout);
	}

	@Override
	public HLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
}
