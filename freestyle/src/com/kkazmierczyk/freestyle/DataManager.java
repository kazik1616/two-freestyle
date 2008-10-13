package com.kkazmierczyk.freestyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * 
 * Class to managing database
 * 
 * @version $Id: DataManager.java 39 2008-06-04 07:48:40Z kazik $
 */
public class DataManager {

	private final static Logger logger = Logger.getLogger(DataManager.class);

	/**
	 * Returns list of visits of given page ordered by visited date from latest
	 * to oldest
	 */
	@SuppressWarnings("unchecked")
	public static Collection<LoggedRequest> getPageLog(
			EntityManagerFactory emf, String path) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em
					.createQuery("from LoggedRequest where path = :path order by date desc");
			q.setParameter("path", path);
			final List<LoggedRequest> result = q.getResultList();

			tx.commit();

			return result;

		} catch (Exception e) {
			logger.error(e);
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return new ArrayList<LoggedRequest>();
	}

	/** Saves visit of bot */
	public static void saveVisitInDatabase(EntityManagerFactory emf,
			String agent, String path) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			final LoggedRequest req = new LoggedRequest(agent, path);
			em.persist(req);
			tx.commit();
		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			logger.error("Error during adding element to database", e);

		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Returns number of nodes visited by given text which is contained by
	 * browser agent name grouped by level of the tree. Each element of the list
	 * is an array which first index points at level and second one on number of
	 * nodes visited
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getNodeNumberVisitedByLevel(
			EntityManagerFactory emf, String agent) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em
					.createQuery("select length(lr.path), count(distinct lr.path) "
							+ "from LoggedRequest lr "
							+ "where lr.agent like :agent group by length(path) "
							+ "order by 1");

			q.setParameter("agent", "%" + agent + "%");
			final List<Object[]> result = q.getResultList();

			tx.commit();

			return result;

		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			logger.error("Error during fetching stats", e);

			return new ArrayList<Object[]>(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Returns number of nodes visited by given text which is contained by
	 * browser agent name grouped by level of the tree. Each element of the list
	 * is an array which first index points at level and second one on number of
	 * nodes visited
	 * 
	 * @param number
	 *            Number of results to display
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getMostVisitedNodes(EntityManagerFactory emf,
			String agent, int number) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em.createQuery("select lr.path, count(*) "
					+ "from LoggedRequest lr "
					+ "where lr.agent like :agent group by lr.path "
					+ "order by 2 desc");

			q.setParameter("agent", "%" + agent + "%");

			List<Object[]> result = q.getResultList();

			if (result.size() > number) {
				result = result.subList(0, number);
			}

			tx.commit();

			return result;

		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			logger.error("Error during fetching stats", e);

			return new ArrayList<Object[]>(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Deepest visited nodes with number of visits by given text which is
	 * contained by browser agent name grouped by level of the tree. Each
	 * element of the list is an array which first index points at level and
	 * second one on number of nodes visited
	 * 
	 * @param number
	 *            Number of results to display
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getDeepstVisitedNodes(
			EntityManagerFactory emf, String agent, int number) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em.createQuery("select lr.path, count(*) "
					+ "from LoggedRequest lr "
					+ "where lr.agent like :agent group by lr.path "
					+ "order by length(lr.path) desc, 2 desc");

			q.setParameter("agent", "%" + agent + "%");

			List<Object[]> result = q.getResultList();

			if (result.size() > number) {
				result = result.subList(0, number);
			}

			tx.commit();

			return result;

		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			logger.error("Error during fetching stats", e);

			return new ArrayList<Object[]>(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Deepest visited nodes with number of visits by given text which is
	 * contained by browser agent name grouped by level of the tree. Each
	 * element of the list is an array which first index points at level and
	 * second one on number of nodes visited
	 * 
	 * @param number
	 *            Number of results to display
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getVisitsByDay(EntityManagerFactory emf,
			String agent) {

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			final Query q = em
					.createQuery("select year(date), month(date), day(date), "
							+ "count(*) " + "from LoggedRequest lr "
							+ "where lr.agent like :agent "
							+ "group by year(date), month(date), day(date) "
							+ "order by 1, 2, 3");

			q.setParameter("agent", "%" + agent + "%");

			List<Object[]> result = q.getResultList();

			tx.commit();

			return result;

		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			logger.error("Error during fetching stats", e);

			return new ArrayList<Object[]>(0);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
