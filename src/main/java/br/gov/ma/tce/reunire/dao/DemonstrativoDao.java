package br.gov.ma.tce.reunire.dao;

import java.util.List;

public interface DemonstrativoDao<T> {
	
	public List<T> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer exercicio);
	
	public String getNomeRelatorio();
}
