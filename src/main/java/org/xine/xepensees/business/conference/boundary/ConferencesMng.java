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
		
		return repository.save(conference);
	}

	public Collection<Conference> all() {
		return Collections.unmodifiableList(repository.all());
	}
	
	public List<Conference> search(QueryParameter parameter) {
		return Collections.unmodifiableList(repository.search(parameter));
	}

	public Conference getConference(Long id) {
		return repository.get(id);
	}
	
	

}
