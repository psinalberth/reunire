package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

public class RelatorioD006AVO {
	
	private Integer idUnidade;
	private String descricaoUnidade;
	private Integer idOrgao;
	private String descricaoOrgao;
	private String funcaoGoverno;
	private String subfuncaoGoverno;
	private String programa;
	private String acao;
	private String nomeFuncao;
	private String nomeSubFuncao;
	private String nomePrograma;
	private String nomeAcao;
	private BigDecimal valorProjeto;
	private BigDecimal valorAtividade;
	private BigDecimal valorOperacoesEspeciais;
	
	
	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade) {
		this.descricaoUnidade = descricaoUnidade;
	}
	
	public Integer getIdOrgao() {
		return idOrgao;
	}
	
	public void setIdOrgao(Integer idOrgao) {
		this.idOrgao = idOrgao;
	}
	
	public String getDescricaoOrgao() {
		return descricaoOrgao;
	}
	
	public void setDescricaoOrgao(String descricaoOrgao) {
		this.descricaoOrgao = descricaoOrgao;
	}

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

	public BigDecimal getValorProjeto() {
		return valorProjeto;
	}

	public void setValorProjeto(BigDecimal valorProjeto) {
		this.valorProjeto = valorProjeto;
	}

	public BigDecimal getValorAtividade() {
		return valorAtividade;
	}

	public void setValorAtividade(BigDecimal valorAtividade) {
		this.valorAtividade = valorAtividade;
	}

	public BigDecimal getValorOperacoesEspeciais() {
		return valorOperacoesEspeciais;
	}

	public void setValorOperacoesEspeciais(BigDecimal valorOperacoesEspeciais) {
		this.valorOperacoesEspeciais = valorOperacoesEspeciais;
	}

	

}
