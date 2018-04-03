package br.gov.ma.tce.reunire.dao.impl.gestor;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.impl.GestoresDaoImpl;

@Stateless
public class ConsultasGestoresImpl<T> extends GestoresDaoImpl<T> {
	
	public T byId(Class<T> clazz, Integer id) {
		return entityManager.find(clazz, id);	
	}
}
