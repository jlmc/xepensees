package org.xine.xepensees.business.conference.boundary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.xepensees.business.conference.control.ConferencesRepository;
import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.params.entity.QueryParameter;

@Stateless
public class ConferencesMng {
	
	@Inject
	ConferencesRepository repository;


	public Conference create(final Conference conference) {
		if (conference == null) {
			throw new IllegalArgumentException("the conference should be not Null");
		}
		
		return this.repository.save(conference);
	}

	public Collection<Conference> all() {
		return Collections.unmodifiableList(this.repository.all());
	}
	
	public List<Conference> search(QueryParameter parameter) {
		return Collections.unmodifiableList(this.repository.search(parameter));
		
		
//		filter.getFirtsResult();
//		filter.getMaxResults();
		
		//int total = this.conferences.size();
//		
//		int numberOfPages = total % pageSize > 0 ?  total / pageSize : total / pageSize + 1;
//		int firtsElement = pageNumber * pageSize;
		
//		List<Conference> currentItens = 
//				Collections.unmodifiableList(
//						this.conferences.stream().
//						skip(filter.getFirtsResult()).
//						limit(filter.getMaxResults()).
//						collect(Collectors.toCollection(ArrayList::new)));
//		
//		return currentItens;
	}

	public Conference getConference(Long id) {
		return this.repository.get(id);
		
		//return this.conferences.stream().filter( c -> c.getId().equals(id)).findAny().map(c-> c ).orElse(Conference.empty());
	}
	
	

}