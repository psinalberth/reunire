package br.gov.ma.tce.reunire.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GestoresDaoImpl <T> {

	@PersistenceContext(unitName="gestor")
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
