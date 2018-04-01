package br.gov.ma.tce.reunire.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PrestacaoDaoImpl<T> {
	
	@PersistenceContext(unitName="prestacao")
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
