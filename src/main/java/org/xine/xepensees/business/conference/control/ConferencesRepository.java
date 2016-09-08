package org.xine.xepensees.business.conference.control;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.persistence.control.CriteriaHelper;

public class ConferencesRepository {

	@PersistenceContext(unitName = "x-expensees")
	EntityManager entityManager;

	@Inject
	CriteriaHelper criteriaHelper;

	public Conference save(Conference conference) {
		return this.entityManager.merge(conference);
	}

	public List<Conference> all() {
		final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Conference> query = builder.createQuery(Conference.class);
		final Root<Conference> conferences = query.from(Conference.class);
		query.select(conferences);
		
		return this.entityManager.createQuery(query).getResultList();
	}

	public List<Conference> search(QueryParameter parameter) {
		final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Conference> query = builder.createQuery(Conference.class);
		final Root<Conference> conferences = query.from(Conference.class);
		query.select(conferences);
		
		final List<Predicate> predicates = parameter.parameters().entrySet().stream().map(e -> {
			if ("name".equalsIgnoreCase(String.valueOf(e.getKey()))) {
				final Predicate ofName = this.criteriaHelper.ilike(builder, conferences.get("name"),
						String.valueOf(e.getValue()), CriteriaHelper.MatchMode.START);
				return Optional.of(ofName);
			}
			return Optional.<Predicate>empty();
		}).flatMap(o -> o.map(e -> Stream.of(e)).orElse(Stream.empty())).
		   collect(Collectors.toList());

		query.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<Conference> createQuery = this.entityManager.createQuery(query);
		createQuery.setFirstResult(parameter.getFirtsRecord());
		createQuery.setMaxResults(parameter.getPageLength());
		
		return createQuery.getResultList();
	}

	public Conference get(Long id) {
		return this.entityManager.find(Conference.class, id);
	}

}
