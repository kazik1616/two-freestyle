package com.kkazmierczyk.freestyle;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** This servlet is used to display data to reports of visits by day */
public class ReportsServlet extends HttpServlet {

	private EntityManagerFactory emf;
	private Bots bots;

	@Override
	public void init() throws ServletException {
		super.init();
		emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		bots = (Bots) getServletContext().getAttribute("bots");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		final Writer writer = new OutputStreamWriter(resp.getOutputStream(),
				"utf-8");

		writer.write("<html><head><title>Data to reports</title></head><body>");

		writer.write("<table><tr>");

		for (Bot bot : bots.getBots()) {
			
			writer.write("<td>");
			List<Object[]> dataToDisplay = DataManager.getVisitsByDay(emf, bot
					.getAgent());
			
			writer.write(bot.getName());
			
			writer.write("<table>");
			
			for (Object[] objects : dataToDisplay) {
				writer.write("<tr><td>" + objects[0] + "</td><td>" + objects[1]
						+ "</td><td>" + objects[2] + "</td><td>" + objects[3]
						+ "</td></tr>");
			}
			
			writer.write("</table>");
			writer.write("</td>");
		}

		writer.write("</tr></table>");

		writer.write("</body></html>");
		writer.close();
	}
}
