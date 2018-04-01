package br.gov.ma.tce.reunire.dao;

import java.util.List;
import java.util.stream.Collectors;

import br.gov.ma.tce.reunire.dao.impl.gestor.UnidadeVODaoImpl;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
import br.gov.ma.tce.reunire.util.Lookup;

public interface DemonstrativoDao<T> {
	
	public List<T> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer exercicio);
	
	public String getNomeRelatorio();
	
	public default List<Integer> recuperarIdsUnidades(Integer ente, Integer orgao, Integer unidadeGestora) {
		
		List<Integer> listaIdsUnidades = null;
		
		UnidadeVODaoImpl dao = Lookup.dao(UnidadeVODaoImpl.class);
		
		if (ente != null) {
			listaIdsUnidades = extrairIds(dao.byEnte(ente));
			
		} else if (orgao != null) {
			listaIdsUnidades = extrairIds(dao.byOrgao(orgao));
			
		} else if (unidadeGestora != null) {
			listaIdsUnidades = extrairIds(dao.byPoder(ente, orgao));
		}
		
		return listaIdsUnidades;
	}
	
	/**
	 * Retorna uma coleção de ids de uma coleção (em geral utilizado para pesquisas).
	 * 
	 * @param unidades Coleção de unidades a ter seus identificadores extraídos.
	 * @return Retorna uma coleção de ids da coleção de Unidades.
	 */
	static List<Integer> extrairIds(List<UnidadeVO> unidades) {
		return unidades.stream().map(unidade -> unidade.getId()).collect(Collectors.toList());
	}
}