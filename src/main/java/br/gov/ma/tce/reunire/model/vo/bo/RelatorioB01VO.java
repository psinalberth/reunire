package br.gov.ma.tce.reunire.model.vo.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioB01VO implements Serializable {
	
	private static final long serialVersionUID = 5062856128767170139L;
	
	private String total;
	private String subtotal;
	private String totalCategoria;
	private String categoria;
	private String origem;
	private String especie;
	private BigDecimal previsaoInicial;
	private BigDecimal previsaoAtualizada;
	private BigDecimal receitasRealizadas;
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getSubtotal() {
		return subtotal;
	}
	
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	
	public String getTotalCategoria() {
		return totalCategoria;
	}
	
	public void setTotalCategoria(String totalCategoria) {
		this.totalCategoria = totalCategoria;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public String getEspecie() {
		return especie;
	}
	
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	
	public BigDecimal getPrevisaoInicial() {
		return previsaoInicial;
	}
	
	public void setPrevisaoInicial(BigDecimal previsaoInicial) {
		this.previsaoInicial = previsaoInicial;
	}
	
	public BigDecimal getPrevisaoAtualizada() {
		return previsaoAtualizada;
	}
	
	public void setPrevisaoAtualizada(BigDecimal previsaoAtualizada) {
		this.previsaoAtualizada = previsaoAtualizada;
	}
	
	public BigDecimal getReceitasRealizadas() {
		return receitasRealizadas;
	}
	
	public void setReceitasRealizadas(BigDecimal receitasRealizadas) {
		this.receitasRealizadas = receitasRealizadas;
	}
}