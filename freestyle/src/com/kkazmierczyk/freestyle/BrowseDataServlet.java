package com.kkazmierczyk.freestyle;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This servlet is used to browse all data
 * 
 * @version $Id: BrowseDataServlet.java 13 2008-05-29 16:13:34Z kazik $
 */
public class BrowseDataServlet extends HttpServlet {

	/** Logger instance. */
	private final static Logger logger = Logger
			.getLogger(BrowseDataServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		EntityManager em = null;
		EntityTransaction tx = null;
		Writer writer = null;

		try {

			EntityManagerFactory emf = (EntityManagerFactory) getServletContext()
					.getAttribute("emf");
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em.createQuery("from LoggedRequest");
			final List<LoggedRequest> result = q.getResultList();

			tx.commit();

			resp.setHeader("Content-type", "text/html; charset=utf-8");

			writer = new OutputStreamWriter(resp.getOutputStream(), "utf-8");

			writer.write("<html><head>"
					+ "<meta http-equiv=\"content-type\" content=\"utf-8\">"
					+ "<meta name=\"robots\" content=\"none\">"
					+ "</head><body>");

			writer.write("<table>");

			for (LoggedRequest loggedRequest : result) {
				writer.write("<tr><td>" + loggedRequest.getAgent()
						+ "</td><td>" + loggedRequest.getPath() + "</td><td>"
						+ loggedRequest.getDate() + "</tr>");
			}

			writer.write("</table>");

			writer.write("</body></html>");

		} catch (Exception e) {
			logger.error(e);
		} finally {

			if (writer != null) {
				writer.close();
			}

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
}
