package org.kobic.gwt.smart.closha.client.statistic.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.statistic.presenter.StatisticPresenter;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.Legend.Align;
import org.moxieapps.gwt.highcharts.client.Legend.Layout;
import org.moxieapps.gwt.highcharts.client.Legend.VerticalAlign;
import org.moxieapps.gwt.highcharts.client.PlotLine.DashStyle;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Chart.ZoomType;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;

import com.smartgwt.client.widgets.layout.VLayout;

public class StatisticViewer extends VLayoutWidget implements StatisticPresenter.Display{

	@SuppressWarnings("rawtypes")
	public StatisticViewer(){
		
		setMargin(30);
		
		Chart chart = new Chart();
		chart.setBackgroundColor("#FFFFFF");
		chart.setBorderColor("#FFFFFF");
		
		chart.setZoomType(ZoomType.X_AND_Y);
		chart.setChartTitleText("CLOSHA");
		chart.setChartSubtitleText("CLOSHA Statistics System");
		
		chart.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
			
			@Override
			public String format(ToolTipData toolTipData) {
				// TODO Auto-generated method stub
				String unit = "";
				
				if(toolTipData.getSeriesName().equals("core")){
					unit = "core";
				}else if(toolTipData.getSeriesName().equals("man")){
					unit = "man";
				}else if(toolTipData.getSeriesName().equals("time(h)")){
					unit = "time(h)";	
				}
				return toolTipData.getXAsString() + " : " + toolTipData.getYAsLong() + " " +unit;
			}
		}));
		
		chart.getXAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		
		chart.getYAxis(0).setGridLineWidth(0);
		chart.getYAxis(0).setAxisTitleText("Core usage");
		chart.getYAxis(0).setOption("/series/color", "#89A54E");
		chart.getYAxis(0).setLabels(new YAxisLabels().setFormatter(new AxisLabelsFormatter() {
			@Override
			public String format(AxisLabelsData axisLabelsData) {
				// TODO Auto-generated method stub
				return axisLabelsData.getValueAsString() + "core";
			}
		}));
		chart.getYAxis(0).setLabels(new YAxisLabels().setColor("#89A54E"));
		chart.getYAxis(0).setOpposite(true);
		
		chart.getYAxis(1).setGridLineWidth(0);
		chart.getYAxis(1).setAxisTitleText("Access user count");
		chart.getYAxis(1).setOption("/series/color", "#4572A7");
		chart.getYAxis(1).setLabels(new YAxisLabels().setFormatter(new AxisLabelsFormatter() {
			@Override
			public String format(AxisLabelsData axisLabelsData) {
				// TODO Auto-generated method stub
				return axisLabelsData.getValueAsString() + "man";
			}
		}));
		chart.getYAxis(1).setLabels(new YAxisLabels().setColor("#4572A7"));
		
		chart.getYAxis(2).setGridLineWidth(0);
		chart.getYAxis(2).setAxisTitleText("Required working time");
		chart.getYAxis(2).setOption("/series/color", "#AA4643");
		chart.getYAxis(2).setLabels(new YAxisLabels().setFormatter(new AxisLabelsFormatter() {
			@Override
			public String format(AxisLabelsData axisLabelsData) {
				// TODO Auto-generated method stub
				return axisLabelsData.getValueAsString() + "h";
			}
		}));
		chart.getYAxis(2).setLabels(new YAxisLabels().setColor("#AA4643"));
		chart.getYAxis(2).setOpposite(true);
		
		Legend chartLengend = new Legend();
		chartLengend.setLayout(Layout.VERTICAL);
		chartLengend.setVerticalAlign(VerticalAlign.TOP);
		chartLengend.setAlign(Align.LEFT);
		chartLengend.setX(120);
		chartLengend.setY(80);
		chartLengend.setFloating(true);
		chartLengend.setBackgroundColor("#FFFFFF");
		
		chart.setLegend(chartLengend);
		
		final Series serise_1 = chart.createSeries()
				   .setPoints(new Number[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4});
		serise_1.setName("core");
		serise_1.setOption("/series/color", "#4572A7");
		serise_1.setType(Series.Type.COLUMN);
		serise_1.setYAxis(1);
		chart.addSeries(serise_1);
		
		final Series serise_2 = chart.createSeries()
				   .setPoints(new Number[] {1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5, 1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.7});
		serise_2.setName("time(h)");
		serise_2.setOption("/series/color", "#AA4643");
		serise_2.setYAxis(2);
		serise_2.setOption("/series/dashStyle", "shortdot");
	
		Marker marker_2 = new Marker();
		marker_2.setEnabled(true);
		marker_2.setOption("symbol", "url(images/closha/icon/chronometer.png)");
		
		PlotOptions<PlotOptions> plotOption_2 = new PlotOptions<PlotOptions>(){};
		plotOption_2.setDashStyle(DashStyle.SHORT_DASH);		
		plotOption_2.setMarker(marker_2);
		
		serise_2.setPlotOptions(plotOption_2);
		
		serise_2.setType(Series.Type.SPLINE);
		chart.addSeries(serise_2);
		
		final Series serise_3 = chart.createSeries()
				   .setPoints(new Number[] { 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6});
		serise_3.setName("man");
		serise_3.setOption("/series/color", "#89A54E");
		serise_3.setType(Series.Type.SPLINE);
		serise_3.setYAxis(0);
		
		Marker marker_3 = new Marker();
		marker_3.setEnabled(true);
		marker_3.setOption("symbol", "url(images/closha/icon/access_user.png)");
		
		PlotOptions<PlotOptions> plotOption_3 = new PlotOptions<PlotOptions>(){};
		plotOption_3.setDashStyle(DashStyle.SOLID);		
		plotOption_3.setMarker(marker_3);
		
		serise_3.setPlotOptions(plotOption_3);
		
		serise_3.setType(Series.Type.SPLINE);
		
		chart.addSeries(serise_3);
		
		addMember(chart);
	}

	@Override
	public VLayout asLayout() {
		// TODO Auto-generated method stub
		return this;
	}
}
