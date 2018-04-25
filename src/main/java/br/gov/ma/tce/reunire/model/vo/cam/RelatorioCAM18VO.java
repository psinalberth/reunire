package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM18VO extends DemonstrativoVO {
	
	private BigDecimal valorCredito;
	private BigDecimal valorDivida;
	
	public BigDecimal getValorCredito() {
		return valorCredito;
	}
	
	public void setValorCredito(BigDecimal valorCredito) {
		this.valorCredito = valorCredito;
	}
	
	public BigDecimal getValorDivida() {
		return valorDivida;
	}
	
	public void setValorDivida(BigDecimal valorDivida) {
		this.valorDivida = valorDivida;
	}
}
