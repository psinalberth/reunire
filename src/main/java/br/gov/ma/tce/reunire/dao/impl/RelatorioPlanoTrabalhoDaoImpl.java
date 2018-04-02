package br.gov.ma.tce.reunire.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.model.vo.RelatorioPlanoTrabalhoVO;

@Stateless
public class RelatorioPlanoTrabalhoDaoImpl extends SaeDaoImpl<RelatorioPlanoTrabalhoVO> implements DemonstrativoDao<RelatorioPlanoTrabalhoVO>{

		@SuppressWarnings("unchecked")
		@Override
		public List<RelatorioPlanoTrabalhoVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
				Integer exercicio) {
			
			String sql = "SELECT DOT.UNIDADE_GESTORA DOT_UNIDADE_GESTORA, FG.CODIGO FG_CODIGO, FG.DESCRICAO FG_DESCRICAO, SFG.CODIGO SFG_CODIGO, SFG.DESCRICAO SFG_DESCRICAO, P.CODIGO P_CODIGO, P.DENOMINACAO P_DENOMINACAO, A.CODIGO_PREFEITURA A_CODIGO_PREFEITURA, A.DESCRICAO A_DESCRICAO, DAN.VALOR DAN_VALOR " + 
					"	FROM SAE_DOTACAO DOT " + 
					"		INNER JOIN SAE_DOTACAO_ACAO_NATUREZA DAN ON DOT.ID_DOTACAO = DAN.ID_DOTACAO " + 
					"		INNER JOIN SAE_ACAO A ON DAN.ID_ACAO = A.ID_ACAO " + 
					"		INNER JOIN SAE_PROGRAMA P ON A.ID_PROGRAMA = P.ID_PROGRAMA " + 
					"		INNER JOIN SAE_FUNCAO_SUB_FUNCAO FSF ON A.ID_FUNCAO_SUB_FUNCAO = FSF.ID_FUNCAO_SUB_FUNCAO " + 
					"		INNER JOIN SAE_FUNCAO_GOVERNO FG ON FSF.ID_FUNCAO_GOVERNO = FG.ID_FUNCAO_GOVERNO " + 
					"		INNER JOIN SAE_SUB_FUNCAO_GOVERNO SFG ON FSF.ID_SUB_FUNCAO_GOVERNO = SFG.ID_SUB_FUNCAO_GOVERNO " + 
					"		WHERE DOT.UNIDADE_GESTORA = :unidade ORDER BY DOT_UNIDADE_GESTORA, P_CODIGO, A_CODIGO_PREFEITURA";
			
			
			List<RelatorioPlanoTrabalhoVO> listaPlanoRelatorioPojo = new ArrayList<>(); 
			
			List<Object[]> listaPlanoRelatorio = entityManager.createNativeQuery(sql)						
					.setParameter("unidade", 150016034)
					.getResultList();
			
			for(Object[] l : listaPlanoRelatorio) {
				RelatorioPlanoTrabalhoVO relatorioPojo = new RelatorioPlanoTrabalhoVO();
				relatorioPojo.setIdUnidadeGestora(Integer.parseInt(l[0].toString()));
				relatorioPojo.setCodigoFuncao(l[1].toString());
				relatorioPojo.setNomeFuncao(l[2].toString());
				relatorioPojo.setCodigoSubfuncao(l[3].toString());
				relatorioPojo.setNomeSubFuncao(l[4].toString());
				relatorioPojo.setCodigoPrograma(l[5].toString());
				relatorioPojo.setNomePrograma(l[6].toString());
				relatorioPojo.setCodigoAcao(l[7].toString());
				relatorioPojo.setNomeAcao(l[8].toString());
				relatorioPojo.setValorExercicio(new BigDecimal(l[9].toString()));
				listaPlanoRelatorioPojo.add(relatorioPojo);
			}	
			
			return listaPlanoRelatorioPojo;
		}

		@Override
		public String getNomeRelatorio() {
			return "RelatorioPlanoTrabalho.jasper";
		}		
		
}
