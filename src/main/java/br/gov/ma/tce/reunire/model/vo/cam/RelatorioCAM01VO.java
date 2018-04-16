package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM01VO extends DemonstrativoVO {
	
	private String codigoNaturezaReceita;
	private String descricao;
	private BigDecimal valorAnoMenos3;
	private BigDecimal valorAnoMenos2;
	private BigDecimal valorAnoMenos1;
	private BigDecimal valorAno;
	private BigDecimal valorAnoMais1;
	private BigDecimal valorAnoMais2;
	
	public String getCodigoNaturezaReceita() {
		return codigoNaturezaReceita;
	}
	
	public void setCodigoNaturezaReceita(String codigoNaturezaReceita) {
		this.codigoNaturezaReceita = codigoNaturezaReceita;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getValorAnoMenos3() {
		return valorAnoMenos3;
	}
	
	public void setValorAnoMenos3(BigDecimal anoMenos3) {
		this.valorAnoMenos3 = anoMenos3;
	}
	
	public BigDecimal getValorAnoMenos2() {
		return valorAnoMenos2;
	}
	
	public void setValorAnoMenos2(BigDecimal anoMenos2) {
		this.valorAnoMenos2 = anoMenos2;
	}
	
	public BigDecimal getValorAnoMenos1() {
		return valorAnoMenos1;
	}
	
	public void setValorAnoMenos1(BigDecimal anoMenos1) {
		this.valorAnoMenos1 = anoMenos1;
	}
	
	public BigDecimal getValorAno() {
		return valorAno;
	}
	
	public void setValorAno(BigDecimal ano) {
		this.valorAno = ano;
	}
	
	public BigDecimal getValorAnoMais1() {
		return valorAnoMais1;
	}
	
	public void setValorAnoMais1(BigDecimal anoMais1) {
		this.valorAnoMais1 = anoMais1;
	}
	
	public BigDecimal getValorAnoMais2() {
		return valorAnoMais2;
	}
	
	public void setValorAnoMais2(BigDecimal anoMais2) {
		this.valorAnoMais2 = anoMais2;
	}
}
