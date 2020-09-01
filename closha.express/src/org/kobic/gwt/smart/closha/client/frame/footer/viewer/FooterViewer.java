package org.kobic.gwt.smart.closha.client.frame.footer.viewer;

import org.kobic.gwt.smart.closha.client.frame.footer.presenter.FooterPresenter;

import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.VLayout;

public class FooterViewer implements FooterPresenter.Display {

	VLayout bottomLayout;

	public FooterViewer() {
		bottomLayout = new VLayout();
		bottomLayout.setWidth100();
		bottomLayout.setHeight(70);
		
		HTMLPane htmlPane = new HTMLPane();
		htmlPane.setBackgroundColor("#3c3f45");
		htmlPane.setOpacity(50);
		
		String contents = "<p align=\"center\">"
				+ "125 Gwahangno, Yuseong-gu, Daejeon, 305-806, Korea. Tel: 82-42-879-8511, Fax: 82-42-879-8519<br>"
				+ "COPYRIGHT(C) Korean Bioinformation Center (KOBIC), All Rights Reserved. <b>E-mail: <a href='mailto:webmaster@kobic.kr'>webmaster@kobic.kr</a></b></p>";
		htmlPane.setContents(contents);
		
		bottomLayout.addMember(htmlPane);
	}

	@Override
	public VLayout getBottomLayout() {
		// TODO Auto-generated method stub
		return bottomLayout;
	}
}
