package br.gov.ma.tce.reunire.model.vo.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioB03VO implements Serializable {

	private static final long serialVersionUID = -2172978567149444822L;
	
	private String categoria;
	private String grupo;
	private BigDecimal restosExercicioAnterior;
	private BigDecimal restosTrintaEUm;
	private BigDecimal restosLiquidados;
	private BigDecimal restosPagos;
	private BigDecimal restosCancelados;
	
	public RelatorioB03VO(String categoria, String grupo, BigDecimal restosExercicioAnterior, BigDecimal restosTrintaEUm, 
						  BigDecimal restosLiquidados, BigDecimal restosPagos, BigDecimal restosCancelados) {
		
		this.categoria = categoria;
		this.grupo = grupo;
		this.restosExercicioAnterior = restosExercicioAnterior;
		this.restosTrintaEUm = restosTrintaEUm;
		this.restosLiquidados = restosLiquidados;
		this.restosPagos = restosPagos;
		this.restosCancelados = restosCancelados;
	}

	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public BigDecimal getRestosExercicioAnterior() {
		return restosExercicioAnterior;
	}
	
	public void setRestosExercicioAnterior(BigDecimal restosExercicioAnterior) {
		this.restosExercicioAnterior = restosExercicioAnterior;
	}
	
	public BigDecimal getRestosTrintaEUm() {
		return restosTrintaEUm;
	}
	
	public void setRestosTrintaEUm(BigDecimal restosTrintaEUm) {
		this.restosTrintaEUm = restosTrintaEUm;
	}
	
	public BigDecimal getRestosLiquidados() {
		return restosLiquidados;
	}
	
	public void setRestosLiquidados(BigDecimal restosLiquidados) {
		this.restosLiquidados = restosLiquidados;
	}
	
	public BigDecimal getRestosPagos() {
		return restosPagos;
	}
	
	public void setRestosPagos(BigDecimal restosPagos) {
		this.restosPagos = restosPagos;
	}
	
	public BigDecimal getRestosCancelados() {
		return restosCancelados;
	}
	
	public void setRestosCancelados(BigDecimal restosCancelados) {
		this.restosCancelados = restosCancelados;
	}
}