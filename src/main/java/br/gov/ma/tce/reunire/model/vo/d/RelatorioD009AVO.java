package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioD009AVO extends DemonstrativoVO {
	
	private BigDecimal[] saldos;	
	
	private String orgao;
	
	public RelatorioD009AVO() {		
		
		this.saldos = new BigDecimal[30];
		
		for (int i = 0; i < 30; i++) {
			this.saldos[i] = BigDecimal.ZERO;
		}
	}
	
	public BigDecimal[] getSaldos() {
		return saldos;
	}

	public void setSaldos(BigDecimal[] saldos) {
		this.saldos = saldos;
	}
	
	public String getOrgao() {
		return orgao;
	}
	
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	
	public void addValor(int index, BigDecimal valor) {
		this.saldos[index] = valor;
	}

}
