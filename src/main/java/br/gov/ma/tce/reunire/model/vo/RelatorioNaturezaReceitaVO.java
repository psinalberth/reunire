package br.gov.ma.tce.reunire.model.vo;

import java.math.BigDecimal;

public class RelatorioNaturezaReceitaVO {
	
	private int idunidade;
	
	private String codigo;
	
	private String descricao;
	
	private BigDecimal valorExercicio;

	public int getIdunidade() {
		return idunidade;
	}

	public void setIdunidade(int idunidade) {
		this.idunidade = idunidade;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorExercicio() {
		return valorExercicio;
	}

	public void setValorExercicio(BigDecimal valorExercicio) {
		this.valorExercicio = valorExercicio;
	}

}
