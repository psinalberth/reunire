package br.gov.ma.tce.reunire.model.vo;

import java.math.BigDecimal;

public class RelatorioPlanoTrabalhoVO {

	private int idUnidadeGestora;

	private String nomeUnidadeGestora;

	private String codigoFuncao;

	private String nomeFuncao;

	private String codigoSubfuncao;

	private String nomeSubFuncao;

	private String codigoPrograma;

	private String nomePrograma;

	private String codigoAcao;

	private String nomeAcao;

	private String descricao;

	private BigDecimal valorExercicio;

	public int getIdUnidadeGestora() {
		return idUnidadeGestora;
	}

	public void setIdUnidadeGestora(int idUnidadeGestora) {
		this.idUnidadeGestora = idUnidadeGestora;
	}

	public String getNomeUnidadeGestora() {
		return nomeUnidadeGestora;
	}

	public void setNomeUnidadeGestora(String nomeUnidadeGestora) {
		this.nomeUnidadeGestora = nomeUnidadeGestora;
	}

	public String getCodigoFuncao() {
		return codigoFuncao;
	}

	public void setCodigoFuncao(String codigoFuncao) {
		this.codigoFuncao = codigoFuncao;
	}

	public String getNomeFuncao() {
		return nomeFuncao;
	}

	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}

	public String getCodigoSubfuncao() {
		return codigoSubfuncao;
	}

	public void setCodigoSubfuncao(String codigoSubfuncao) {
		this.codigoSubfuncao = codigoSubfuncao;
	}

	public String getNomeSubFuncao() {
		return nomeSubFuncao;
	}

	public void setNomeSubFuncao(String nomeSubFuncao) {
		this.nomeSubFuncao = nomeSubFuncao;
	}

	public String getCodigoPrograma() {
		return codigoPrograma;
	}

	public void setCodigoPrograma(String codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}

	public String getNomePrograma() {
		return nomePrograma;
	}

	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}

	public String getCodigoAcao() {
		return codigoAcao;
	}

	public void setCodigoAcao(String codigoAcao) {
		this.codigoAcao = codigoAcao;
	}

	public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
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
