package br.gov.ma.tce.reunire.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DAOImpl<T> {
	
	@PersistenceContext(unitName="reunire")
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}