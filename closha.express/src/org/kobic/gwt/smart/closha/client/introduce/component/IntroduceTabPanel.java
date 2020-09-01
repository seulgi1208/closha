package org.kobic.gwt.smart.closha.client.introduce.component;

import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;


public class IntroduceTabPanel extends Tab{
	
	@SuppressWarnings("unused")
	private HTMLPane infoHTMLPane;

	public IntroduceTabPanel(){
		setID(Constants.INTRO_WINDOW_ID);
		setTitle(Constants.INTRO_WINDOW_TITLE);
		setIcon("closha/icon/biotech-16.ico");
		setWidth(200);
		
//		infoHTMLPane = new HTMLPane();
//		infoHTMLPane.setHeight100();
//		infoHTMLPane.setWidth100();
//		infoHTMLPane.setOverflow(Overflow.AUTO);
//		infoHTMLPane.setBorder("solid 1px #DCDCDC");
//		infoHTMLPane.setPadding(5);
//		infoHTMLPane.setAlign(Alignment.CENTER);
//		infoHTMLPane.setEvalScriptBlocks(true);
//		
//		CommonController.Util.getInstance().getHTMLContents("intro/intro.html", new AsyncCallback<String>() {
//			@Override
//			public void onSuccess(String result) {
//				// TODO Auto-generated method stub
//				infoHTMLPane.setContents(result);
//			}
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				SC.say(caught.getMessage());
//			}
//		});
		
//		setPane(infoHTMLPane);
		
		Img img = new Img("closha/img/closha_intro.png");
		img.setHeight(909);
		img.setWidth(1590);
		img.setImageType(ImageStyle.CENTER);
		
		HLayout h = new HLayout();
		h.setHeight100();
		h.setWidth100();
		h.setAlign(Alignment.CENTER);
		h.addMember(img);
		
		setPane(h);
	}
}
