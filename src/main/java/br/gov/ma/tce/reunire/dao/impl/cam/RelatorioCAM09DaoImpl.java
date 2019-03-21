package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM09VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM09DaoImpl extends PrestacaoDaoImpl<RelatorioCAM09VO> implements DemonstrativoDao<RelatorioCAM09VO> {

	@Override
	public List<RelatorioCAM09VO> recuperaDados(Map<String, Object> params) {
		
		String sql =
				
		"select " +
			"unidade_id, (case when length(genero) > 10 then genero else unidade_ensino end) unidade_ensino, nome_aluno, " +
			"(case " +
				"when length(genero) > 10 then unidade_ensino " +
				"when upper(genero) like 'M%' then 'M' " +
				"when upper(genero) like 'F%' then 'F' " +
				"when genero = '1' then 'M' " +
				"when genero = '2' then 'F' " +
				"when genero = 'D' then 'F' " +
				"else genero end) genero, data_nascimento, " + 
			"(case " + 
				"when serie = 1 then '1º ano do Ensino Fundamental' " +
				"when serie = 2 then '2º ano do Ensino Fundamental' " +
				"when serie = 3 then '3º ano do Ensino Fundamental' " +
				"when serie = 4 then '4º ano do Ensino Fundamental' " +
				"when serie = 5 then '5º ano do Ensino Fundamental' " +
				"when serie = 6 then '6º ano do Ensino Fundamental' " +
				"when serie = 7 then '7º ano do Ensino Fundamental' " +
				"when serie = 8 then '8º ano do Ensino Fundamental' " +
				"when serie = 9 then '9º ano do Ensino Fundamental' " +
				"when serie = 10 then '1º ano do Ensino Médio' " +
				"when serie = 11 then '2º ano do Ensino Médio' " +
				"when serie = 12 then '3º ano do Ensino Médio' " +
				"when serie = 13 then 'Creche' " +
				"when serie = 14 then 'Pré-Escola' " +
				"else 'Outros' " +
			"end) serie, " + 
			"(case " + 
				"when situacao = 1 then 'Cursando' " +
				"when situacao = 2 then 'Evadido' " +
				"else 'Outros' " +
			"end) situacao " +
		"from " + 
			"prestacao.cam09 " +
		"where " +
			"unidade_id in (:unidades) " +
		"order by " +
			"unidade_ensino, nome_aluno, genero, data_nascimento, serie, situacao";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);		
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)				
				.getResultList();
		
		List<RelatorioCAM09VO> dados = new ArrayList<RelatorioCAM09VO>();
		
		for (Object [] row : rows) {
			
			RelatorioCAM09VO dado = new RelatorioCAM09VO();
			
			dado.setIdUnidade(Integer.valueOf(String.valueOf(row[0])));
			dado.setUnidadeEnsino(String.valueOf(row[1]));
			dado.setAluno(String.valueOf(row[2]));
			dado.setGenero(String.valueOf(row[3]));
			dado.setDataNascimento(toDate(row[4]));
			dado.setSerie(String.valueOf(row[5]));
			dado.setSituacao(String.valueOf(row[6]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam09.jasper";
	}
}