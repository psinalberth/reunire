package br.gov.ma.tce.reunire.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.model.vo.RelatorioEnderecoVO;

@Stateless
public class RelatorioEnderecoDaoImpl extends DAOImpl<RelatorioEnderecoVO> implements DemonstrativoDao<RelatorioEnderecoVO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioEnderecoVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora,
			Integer exercicio) {
		
		String sql = "select * from REL_ENDERECO_VO order by CIDADE, BAIRRO, LOGRADOURO asc";
		
		return entityManager.createNativeQuery(sql, RelatorioEnderecoVO.class).getResultList();
	}

	@Override
	public String getNomeRelatorio() {
		return "rel_endereco.jasper";
	}
}