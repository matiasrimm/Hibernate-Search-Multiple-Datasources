package com.haku.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haku.eka.Eka;
import com.haku.toka.Toka;

@Service
public class SearchService {
	
	@Autowired
	@PersistenceContext(unitName="ekaUnit")
	private EntityManager entityManager;
		
	@Transactional(transactionManager = "ekaTransactionManager")
	public List<Eka> searchFromFirst(String keyword){
				
		FullTextEntityManager fullTextEntityManager = 
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);	
		
			final QueryBuilder b = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity( Eka.class ).get();

			org.apache.lucene.search.Query luceneQuery =
			    b.keyword()
			    	.fuzzy()
			    	.withPrefixLength( 0 )
			    	.onFields("info","xinfo")
			        .matching(keyword)
			        .createQuery();
			javax.persistence.Query fullTextQuery = 
			    fullTextEntityManager.createFullTextQuery( luceneQuery, Eka.class );

			@SuppressWarnings("unchecked")
			List<Eka> result = fullTextQuery.getResultList();
			return result;			
	}
	
	@Autowired
	@PersistenceContext(unitName="tokaUnit")
	private EntityManager tokaEntityManager;	
		
	@Transactional(transactionManager = "tokaTransactionManager")
	public List<Toka> searchFromSecond(String keyword){
				
		FullTextEntityManager fullTextEntityManager = 
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(tokaEntityManager);
		
			final QueryBuilder b = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity( Toka.class ).get();

			org.apache.lucene.search.Query luceneQuery =
			    b.keyword()
			    	.fuzzy()
			    	.withPrefixLength( 0 )
			        .onFields("info","xinfo")
			        .matching(keyword)
			        .createQuery();
			javax.persistence.Query fullTextQuery = 
			    fullTextEntityManager.createFullTextQuery( luceneQuery, Toka.class );

			@SuppressWarnings("unchecked")
			List<Toka> result = fullTextQuery.getResultList();
						
			return result;			
	}
	
	
	// for re-indexing 
	public void reIndex (){
		
		FullTextEntityManager fullTextEntityManager = 
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(tokaEntityManager);
		
		createIndexer(fullTextEntityManager);
		
		fullTextEntityManager = 
			    org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		
		createIndexer(fullTextEntityManager);
		
	}
	
	public void createIndexer (FullTextEntityManager fullTextEntityManager){
		
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	

	@Transactional(transactionManager = "ekaTransactionManager")
	public List<Object> wrapSearchQueries (String search) {
		
		List<Eka> elist = searchFromFirst(search);
		List<Toka> tlist = searchFromSecond(search);		
		List<Object> newList = new ArrayList<Object>(elist);
		newList.addAll(tlist);
		
		return newList;
		
	}
	
}
