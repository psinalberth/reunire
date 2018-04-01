package br.gov.ma.tce.reunire.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SaeDaoImpl<T> {
	
	@PersistenceContext(unitName="sae")
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
