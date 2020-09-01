package org.kobic.gwt.smart.closha.client.common.component;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;

public class ShowMessageWindow extends Window{
	
	@Override   
    protected void onInit() {
		Timer timer = new Timer() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				destroy();
			}
		};
		timer.schedule(10000);	
	}
	
	public ShowMessageWindow(){
		
		Img img = new Img("closha/pop/popup_e_1.gif", 400, 300);  
		img.setImageWidth(400);  
		img.setImageHeight(300);  
		img.setImageType(ImageStyle.CENTER);  
		
        setWidth(412);  
        setHeight(329);  
        setTitle("Notice");
        setHeaderIcon("closha/icon/smartmode_co.gif");
        setShowMinimizeButton(false);  
        setShowCloseButton(true);
        setCanDragReposition(true);
        setAnimateTime(1000);
        setVisibility(Visibility.HIDDEN);
        setLeft(50);
        setTop(100);
        addItem(img);
	}
}