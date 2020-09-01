package org.kobic.gwt.smart.closha.client.common.component;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProgressWindow extends Window{
	
	private int percentValue = 0;  
	private int count = 0;
	
	public ProgressWindow(final String title){
		VLayout progressLayout = new VLayout();
		progressLayout.setHeight100();
		progressLayout.setWidth100();
		progressLayout.setPadding(5);
		
		final Progressbar projectProgressBar = new Progressbar();
		projectProgressBar.setHeight(20);
		projectProgressBar.setWidth(300);
		projectProgressBar.setVertical(false);
		progressLayout.addMember(projectProgressBar);
		
		setWidth(323);
		setHeight(60);		
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setTitle(title);
		setHeaderIcon("closha/icon/project_loading.png");
		
		addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub
				percentValue = 0;  
				projectProgressBar.setPercentDone(percentValue);
				
				Timer timer = new Timer() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						percentValue += 5;
						if (percentValue >= 100){  
                            percentValue = 100;  
						}else{
							schedule(50);
						}
						projectProgressBar.setPercentDone(percentValue);
						setTitle(title + ".. ( " + percentValue + "% )");
					}
				};
				timer.schedule(50);	
			}
		});
		addItem(progressLayout);
	}
	
	public ProgressWindow(final String title, final boolean loop){
		
		VLayout progressLayout = new VLayout();
		progressLayout.setHeight100();
		progressLayout.setWidth100();
		progressLayout.setPadding(5);
		
		final Progressbar projectProgressBar = new Progressbar();
		projectProgressBar.setHeight(20);
		projectProgressBar.setWidth(300);
		projectProgressBar.setVertical(false);
		progressLayout.addMember(projectProgressBar);
		
		setWidth(323);
		setHeight(60);		
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setHeaderIcon("closha/icon/file_downloading.png");
		
		addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub
				percentValue = 0;  
				projectProgressBar.setPercentDone(percentValue);
				
				Timer timer = new Timer() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						percentValue += 1;
						if (percentValue >= 100){  
                            percentValue = 0;  
						}
						schedule(40);
						projectProgressBar.setPercentDone(percentValue);
						setTitle(title + ".. ( " + percentValue + "% )");
					}
				};
				timer.schedule(50);
			}
		});
		addItem(progressLayout);
	}
	
	public ProgressWindow(final String title, final String content, final int schedule){
		
		final Label label = new Label(content);
		label.setHeight(20);
		
		final Progressbar projectProgressBar = new Progressbar();
		projectProgressBar.setHeight(20);
		projectProgressBar.setWidth100();
		projectProgressBar.setVertical(false);
		
		VLayout progressLayout = new VLayout();
		progressLayout.setHeight100();
		progressLayout.setWidth100();
		progressLayout.setPadding(5);
		progressLayout.setMembersMargin(5);
		progressLayout.addMembers(label, projectProgressBar);
		
		setWidth(400);
		setHeight(100);		
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setHeaderIcon("closha/icon/project_sharing.png");
		
		addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub
				percentValue = 0;  
				count = 0;
				projectProgressBar.setPercentDone(percentValue);
				
				Timer timer = new Timer() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						percentValue += 1;
						count += 1 + (int) (10 * Math.random());
						
						if (percentValue >= 100){  
                            percentValue = 0;  
						}
						
						if (percentValue != 100)
							schedule(5 + (int) (50 * Math.random()));

						projectProgressBar.setPercentDone(percentValue);
						setTitle(title + ".." + percentValue + "% (" + count + " item has been copied.)");
					}
				};
				timer.schedule(schedule);
			}
		});
		
		addItem(progressLayout);
	}
}
