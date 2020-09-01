package org.kobic.gwt.smart.closha.server.servlet.download;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnixDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		int length = 0;
		
		try {
			ServletOutputStream op = res.getOutputStream();

			ServletContext context = getServletConfig().getServletContext();

			String output = req.getParameter("output");

			File file = new File(output);
			String fileName = file.getName();

			if (file.exists()) {

				String mimetype = context.getMimeType(fileName);

				res.setContentType((mimetype != null) ? mimetype
						: "application/octet-stream");
				res.setContentLength((int) file.length());
				res.setHeader("Content-Disposition", "attachment; filename=\""
						+ fileName + "\"");

				byte[] bbuf = new byte[(int) file.length()];

				DataInputStream in = new DataInputStream(new FileInputStream(
						file));

				while ((in != null) && ((length = in.read(bbuf)) != -1)) {
					op.write(bbuf, 0, length);
				}

				in.close();
				op.flush();
				op.close();
			} else {
				System.out.println("Download Servlet Error{File not found exceptions!}");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}