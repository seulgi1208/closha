package org.kobic.gwt.smart.closha.server.servlet.output;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("text/html");
		String path = req.getParameter("path");

		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String s;
			
			PrintWriter out = res.getWriter();

			while ((s = in.readLine()) != null) {
				out.println(s);
			}
			
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}

	}
}
