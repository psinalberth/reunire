package br.gov.ma.tce.reunire.dao.impl.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD008VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD008DaoImpl extends PrestacaoDaoImpl<RelatorioD008VO> implements DemonstrativoDao<RelatorioD008VO> {

	@Override
	public List<RelatorioD008VO> recuperaDados(Map<String, Object> params) {
		
		String sql =
				
		"select " + 
		"	emp.unidade_id, emp.funcao_id, emp.subfuncao_id, fg.nome nome_funcao, sf.nome nome_subfuncao,   " + 
		"	(case when prog.id_programa is not null then prog.codigo else cast(emp.programa as integer) end) programa,    " + 
		"	(case when prog.id_programa is not null then prog.denominacao else 'NÃO INFORMADO' end) nome_programa,    " + 
		"	(case when acao.id_acao is not null then acao.codigo_prefeitura else cast(regexp_replace(emp.acao, '(.0000+$)', '', 'g') as decimal) end) acao,   " + 
		"	(case when acao.id_acao is not null then acao.descricao else 'NÃO INFORMADO' end) descricao, " + 
		"	emp.valor_ordinario, emp.valor_vinculado  " + 
		"from " + 
		"	(select  " + 
		"		emp.unidade_id, emp.funcao_id, emp.subfuncao_id, emp.programa, emp.acao,   " + 
		"		coalesce(sum(case when emp.fonte_recurso_id = 1062 then coalesce(emp.valor, 0) + coalesce(ref.valor, 0) - coalesce(anu.valor, 0) end), 0) valor_ordinario,  " + 
		"		coalesce(sum(case when emp.fonte_recurso_id <> 1062 then coalesce(emp.valor, 0) + coalesce(ref.valor, 0) - coalesce(anu.valor, 0) end), 0) valor_vinculado  " + 
		"	from   " + 
		"		remessa.empenho emp   " + 
		"	left join (  " + 
		"		select   " + 
		"			ref.empenho_id, sum(ref.valor) valor  " + 
		"		from   " + 
		"			remessa.empenho_reforco ref  " + 
		"		group by  " + 
		"			ref.empenho_id  " + 
		"	) ref on ref.empenho_id = emp.empenho_id  " + 
		"	left join (  " + 
		"		select   " + 
		"			anu.empenho_id, sum(anu.valor) valor   " + 
		"		from   " + 
		"			remessa.empenho_anulado anu  " + 
		"		group by  " + 
		"			anu.empenho_id  " + 
		"	) anu on anu.empenho_id = emp.empenho_id  " + 
		"	where  " + 
		"		emp.unidade_id in (:unidades) and " + 
		"		emp.valor <> 0 " + 
		"	group by " + 
		"		emp.unidade_id, emp.funcao_id, emp.subfuncao_id, emp.programa, emp.acao " + 
		"	order by  " + 
		"		emp.funcao_id, emp.subfuncao_id, emp.programa, emp.acao) emp " + 
		"inner join gestor.unidade_ente und on   " + 
		"	und.unidade_id = emp.unidade_id  " + 
		"left join remessa.funcao fg on   " + 
		"	fg.funcao_id = emp.funcao_id   " + 
		"left join remessa.subfuncao sf on	   " + 
		"	sf.subfuncao_id = emp.subfuncao_id   " + 
		"left join sae.sae_acao acao on   " + 
		"	cast(acao.codigo_prefeitura as text) = cast(regexp_replace(acao, '(.0000+$)', '', 'g') as text) and  " + 
		"	acao.funcao = emp.funcao_id and   " + 
		"	acao.subfuncao = emp.subfuncao_id and  " + 
		"	acao.unidade = emp.unidade_id " + 
		"left join sae.sae_programa prog on   " + 
		"	prog.id_programa = acao.id_programa " +
		"order by " + 
			"emp.funcao_id, emp.subfuncao_id, programa, acao asc";
		
		List<RelatorioD008VO> dados = new ArrayList<>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
				
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)				
				.getResultList();
		
		for (Object[] row : rows) {
			
			RelatorioD008VO dado = new RelatorioD008VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			dado.setFuncaoGoverno(row[1].toString());
			dado.setSubfuncaoGoverno(row[2].toString());
			dado.setNomeFuncao(row[3] != null ? String.valueOf(row[3]) : "NÃO INFORMADO");
			dado.setNomeSubFuncao(row[4] != null ? String.valueOf(row[4]) : "NÃO INFORMADO");
			dado.setPrograma(row[5] != null ? String.valueOf(row[5]) : "NÃO INFORMADO");
			dado.setNomePrograma(row[6] != null ? String.valueOf(row[6]) : "NÃO INFORMADO");
			dado.setAcao(row[7] != null ? String.valueOf(row[7]) : "NÃO INFORMADO");
			dado.setNomeAcao(row[8] != null ? String.valueOf(row[8]) : "NÃO INFORMADO");
			dado.setValorOrdinario(toBigDecimal(row[9]));
			dado.setValorVinculado(toBigDecimal(row[10]));
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriod008.jasper";
	}
}