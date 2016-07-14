package com.haku;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.Test;
import com.haku.eka.Eka;

import static org.junit.Assert.*;

public class ServiceTests extends OntApplicationTests {	
	
	@PersistenceContext(unitName="ekaUnit")
	EntityManager manager; 
	 
	@Test
	public void checkThatResultNotNull() { 
	 
		FullTextEntityManager fullTextEntityManager = 
					    org.hibernate.search.jpa.Search.getFullTextEntityManager(manager);
			 
		final QueryBuilder b = fullTextEntityManager.getSearchFactory()
					    .buildQueryBuilder().forEntity( Eka.class ).get();
	
		org.apache.lucene.search.Query luceneQuery =
					    b.keyword()
					    	.fuzzy()
					    	.withPrefixLength( 0 )
					        .onFields("info")
					        .matching("info")
					        .createQuery();
		
		javax.persistence.Query fullTextQuery = 
					    fullTextEntityManager.createFullTextQuery( luceneQuery, Eka.class );
	
		@SuppressWarnings("unchecked")
		List<Eka> result = fullTextQuery.getResultList();	  
		 
		assertNotNull(result); 
	 	
	}
}
