package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioD010VO extends DemonstrativoVO {
	
	private String codigoNaturezaReceita;
	private String descricao;
	private BigDecimal valorOrcado;
	private BigDecimal valorArrecadado;
	private BigDecimal valorMais;
	private BigDecimal valorMenos;
	
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
	
	public BigDecimal getValorOrcado() {
		return valorOrcado;
	}
	
	public void setValorOrcado(BigDecimal valorOrcado) {
		this.valorOrcado = valorOrcado;
	}
	
	public BigDecimal getValorArrecadado() {
		return valorArrecadado;
	}
	
	public void setValorArrecadado(BigDecimal valorArrecadado) {
		this.valorArrecadado = valorArrecadado;
	}
	
	public BigDecimal getValorMais() {
		return valorMais;
	}
	
	public void setValorMais(BigDecimal valorMais) {
		this.valorMais = valorMais;
	}
	
	public BigDecimal getValorMenos() {
		return valorMenos;
	}
	
	public void setValorMenos(BigDecimal valorMenos) {
		this.valorMenos = valorMenos;
	}
}