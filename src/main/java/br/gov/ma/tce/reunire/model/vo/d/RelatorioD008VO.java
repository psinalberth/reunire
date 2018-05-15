package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioD008VO extends DemonstrativoVO {

	private String funcaoGoverno;
	private String subfuncaoGoverno;
	private String programa;
	private String acao;
	private String nomeFuncao;
	private String nomeSubFuncao;
	private String nomePrograma;
	private String nomeAcao;
	private BigDecimal valorOrdinario;
	private BigDecimal valorVinculado;
	
	public String getFuncaoGoverno() {
		return funcaoGoverno;
	}
	
	public void setFuncaoGoverno(String funcaoGoverno) {
		this.funcaoGoverno = funcaoGoverno;
	}
	
	public String getSubfuncaoGoverno() {
		return subfuncaoGoverno;
	}
	
	public void setSubfuncaoGoverno(String subfuncaoGoverno) {
		this.subfuncaoGoverno = subfuncaoGoverno;
	}
	
	public String getPrograma() {
		return programa;
	}
	
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	public String getAcao() {
		return acao;
	}
	
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
	public String getNomeFuncao() {
		return nomeFuncao;
	}
	
	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	
	public String getNomeSubFuncao() {
		return nomeSubFuncao;
	}
	
	public void setNomeSubFuncao(String nomeSubFuncao) {
		this.nomeSubFuncao = nomeSubFuncao;
	}
	
	public String getNomePrograma() {
		return nomePrograma;
	}
	
	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}
	
	public String getNomeAcao() {
		return nomeAcao;
	}
	
	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}
	
	public BigDecimal getValorOrdinario() {
		return valorOrdinario;
	}
	
	public void setValorOrdinario(BigDecimal valorOrdinario) {
		this.valorOrdinario = valorOrdinario;
	}
	
	public BigDecimal getValorVinculado() {
		return valorVinculado;
	}
	
	public void setValorVinculado(BigDecimal valorVinculado) {
		this.valorVinculado = valorVinculado;
	}
}