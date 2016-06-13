package org.xine.xepensees.business.conferences.boundary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.xepensees.business.conferences.control.ConferencesRepository;
import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.conferences.entity.ConferenceFilter;

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
	
	public List<Conference> search(ConferenceFilter filter) {
		return Collections.unmodifiableList(this.repository.search(filter));
		
		
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
