package org.xine.xepensees.business.conferences.boundary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.conferences.entity.ConferenceFilter;

@Singleton
public class ConferenceMng {

	AtomicLong sec = new AtomicLong(0);

	Set <Conference> conferences = new HashSet<>();
	
	@PostConstruct
	public void setUp() {
		for (int i = 0; i < 150; i++) {
			final Conference conference = Conference.empty();

			final long id = this.sec.getAndIncrement();
			conference.setId(id);
			conference.setName("Abc " + id);
			conference.setCountry("Portugal");
			conference.setCity("Coimbra");
			conference.setDate(LocalDate.now().plusDays(id));

			this.conferences.add(conference);
		}
	}

	public Conference create(final Conference conference) {
		conference.setId(this.sec.incrementAndGet());
		this.conferences.add(conference);
		return conference;
	}

	public Collection<Conference> all() {
		return Collections.unmodifiableList(new ArrayList<>(this.conferences));
	}
	
	public List<Conference> search(ConferenceFilter filter) {
		filter.getFirtsResult();
		filter.getMaxResults();
		
		//int total = this.conferences.size();
//		
//		int numberOfPages = total % pageSize > 0 ?  total / pageSize : total / pageSize + 1;
//		int firtsElement = pageNumber * pageSize;
		
		List<Conference> currentItens = 
				Collections.unmodifiableList(
						this.conferences.stream().
						skip(filter.getFirtsResult()).
						limit(filter.getMaxResults()).
						collect(Collectors.toCollection(ArrayList::new)));
		
		return currentItens;
	}

	public Conference getConference(Long id) {
		return this.conferences.stream().filter( c -> c.getId().equals(id)).findAny().map(c-> c ).orElse(Conference.empty());
	}
	
	

}
