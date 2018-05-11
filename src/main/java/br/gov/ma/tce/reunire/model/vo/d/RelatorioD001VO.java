package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioD001VO extends DemonstrativoVO {
	
	private BigDecimal [] receitas;
	private BigDecimal [] despesas;
	
	public BigDecimal[] getReceitas() {
		return receitas;
	}
	
	public void setReceitas(BigDecimal[] receitas) {
		this.receitas = receitas;
	}
	
	public BigDecimal[] getDespesas() {
		return despesas;
	}
	
	public void setDespesas(BigDecimal[] despesas) {
		this.despesas = despesas;
	}
}
