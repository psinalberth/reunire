package br.gov.ma.tce.reunire.model.vo;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM18VO extends DemonstrativoVO {
	
	private String identificacaoDevedor;
	private String nomeDevedor;
	private String tipo;
	private BigDecimal valorPrincipal;
	private BigDecimal valorMulta;
	private BigDecimal valorJuros;

	public String getIdentificacaoDevedor() {
		return identificacaoDevedor;
	}

	public void setIdentificacaoDevedor(String identificacaoDevedor) {
		this.identificacaoDevedor = identificacaoDevedor;
	}

	public String getNomeDevedor() {
		return nomeDevedor;
	}

	public void setNomeDevedor(String nomeDevedor) {
		this.nomeDevedor = nomeDevedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValorPrincipal() {
		return valorPrincipal;
	}

	public void setValorPrincipal(BigDecimal valorPrincipal) {
		this.valorPrincipal = valorPrincipal;
	}

	public BigDecimal getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta) {
		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros) {
		this.valorJuros = valorJuros;
	}
}