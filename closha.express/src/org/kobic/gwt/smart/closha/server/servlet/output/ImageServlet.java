package org.kobic.gwt.smart.closha.server.servlet.output;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		res.setContentType("image/png");
		
		String type = req.getParameter("type");
		String path = req.getParameter("path");

		try {
			BufferedImage bufferedImage = ImageIO.read(new File(path));
			ImageIO.write(bufferedImage, type, res.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
