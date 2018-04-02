package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD002AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD002ADaoImpl extends PrestacaoDaoImpl<RelatorioD002AVO> implements DemonstrativoDao<RelatorioD002AVO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioD002AVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
			Integer exercicio) {
		
		String sql = "select d.unidade_id unidade," + 
				"	   cr.codigo || '.0.0.0.00.00' cod_cat, cr.descricao desc_cat, " +
				"	   cr.codigo || '.'|| o.codigo || '.0.0.00.00' cod_ori, o.descricao desc_ori, " + 
				"	   cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.0.00.00' cod_esp, e.descricao desc_esp, " + 
				"	   cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.00.00' cod_rub, r.descricao desc_rub,  " + 
				"	   cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.' || to_char(a.codigo, 'FM00') || '.00' cod_ali, a.descricao desc_ali, " + 
				"	   sum (d.valor_atual) valor " + 
				"	   from prestacao.d002a d " + 
				"		inner join sae.vw_natureza_receita vw_rc on regexp_replace(d.natureza_receita, '[.]', '', 'g') = regexp_replace(vw_rc.codigo_natureza_receita, '[.]','','g') and vw_rc.ativo = 'S' " + 
				"		inner join sae.sae_natureza_receita nr on nr.id_natureza_receita = vw_rc.id_natureza_receita and nr.ativo = 'S' " + 
				"		inner join sae.sae_categoria_ec_receita cr on cr.id_categoria_ec_receita = nr.id_categoria_ec_receita and cr.ativo = 'S' " + 
				"		inner join sae.sae_origem o on o.id_origem = nr.id_origem and o.ativo = 'S' " + 
				"		inner join sae.sae_especie e on e.id_especie = nr.id_especie and e.ativo = 'S' " + 
				"		inner join sae.sae_rubrica r on r.id_rubrica = nr.id_rubrica and r.ativo = 'S' " + 
				"		inner join sae.sae_alinea a on a.id_alinea = nr.id_alinea and a.ativo = 'S' "+ 
				"		where d.unidade_id in(:unidade)"+
				"		group by unidade, cod_cat, desc_cat, cod_ori, desc_ori, cod_esp, desc_esp, cod_rub, desc_rub, cod_ali, desc_ali "+
				"		order by unidade, cod_cat, cod_ori, cod_esp, cod_rub, cod_ali";
		
		List<RelatorioD002AVO> listaVo = new ArrayList<>();
		List<Integer> listaIdsUnidades = recuperarIdsUnidades(ente, orgao, poder, unidadeGestora);
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(ente, orgao, poder, unidadeGestora);
		

		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioD002AVO relatorio = new RelatorioD002AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setCodigoCategoriaEconomica(l[1].toString());
			relatorio.setDescricaoCategoriaEconomica(l[2].toString());
			relatorio.setCodigoOrigem(l[3].toString());
			relatorio.setDescricaoOrigem(l[4].toString());
			relatorio.setCodigoEspecie(l[5].toString());
			relatorio.setDescricaoEspecie(l[6].toString());
			relatorio.setCodigoRubrica(l[7].toString());
			relatorio.setDescricaoRubrica(l[8].toString());
			relatorio.setCodigoAlinea(l[9].toString());
			relatorio.setDescricaoAlinea(l[10].toString());
			relatorio.setValor(new BigDecimal((new Double(l[11].toString()))));
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "lei4320anexoii_receita.jasper";
	}
		

}
