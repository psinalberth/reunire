package br.gov.ma.tce.reunire.model.vo.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioB01VO implements Serializable {
	
	private static final long serialVersionUID = 5062856128767170139L;
	
	private String ente;
	private String total;
	private String subtotal;
	private String totalCategoria;
	private String categoria;
	private String origem;
	private String especie;
	private BigDecimal previsaoInicial;
	private BigDecimal previsaoAtualizada;
	private BigDecimal receitasRealizadas;
	
	public RelatorioB01VO(String categoria, String origem, String totalCategoria, String especie, BigDecimal previsaoInicial, BigDecimal previsaoAtualizada, BigDecimal receitasAtualizadas) {
		
		this.total = "TOTAL (VII) = (V + VI)";
		this.totalCategoria = totalCategoria;
		this.categoria = categoria;
		this.origem = origem;
		this.especie = especie;
		this.previsaoInicial = previsaoInicial;
		this.previsaoAtualizada = previsaoAtualizada;
		this.receitasRealizadas = receitasAtualizadas;
	}
	
	public String getEnte() {
		return ente;
	}
	
	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public RelatorioB01VO() {
		this.total = "TOTAL (VII) = (V + VI)";
	}

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