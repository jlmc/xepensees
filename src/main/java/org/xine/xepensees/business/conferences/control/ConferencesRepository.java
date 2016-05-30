package org.xine.xepensees.business.conferences.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.conferences.entity.ConferenceFilter;

public class ConferencesRepository {

	@PersistenceContext(unitName = "x-expensees")
	EntityManager entityManager;

	public Conference save(Conference conference) {
		return this.entityManager.merge(conference);
	}

	public List<Conference> all() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Conference> query = builder.createQuery(Conference.class);
		Root<Conference> conferences = query.from(Conference.class);
		query.select(conferences);
		
		return this.entityManager.createQuery(query).getResultList();
	}

	public List<Conference> search(ConferenceFilter filter) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Conference> query = builder.createQuery(Conference.class);
		Root<Conference> conferences = query.from(Conference.class);
		query.select(conferences);
		
		TypedQuery<Conference> createQuery = this.entityManager.createQuery(query);
		createQuery.setFirstResult(filter.getFirtsResult());
		createQuery.setMaxResults(filter.getMaxResults());
		
		return createQuery.getResultList();
		
	}

	public Conference get(Long id) {
		return this.entityManager.find(Conference.class, id);
	}

}
