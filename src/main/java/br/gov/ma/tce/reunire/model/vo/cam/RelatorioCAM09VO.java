package br.gov.ma.tce.reunire.model.vo.cam;

import java.util.Date;

public class RelatorioCAM09VO {
	
	private Integer idUnidade;
	private String unidadeEnsino;
	private String aluno;
	private String genero;
	private Date dataNascimento;
	private String serie;
	private String situacao;
	
	public Integer getIdUnidade() {
		return idUnidade;
	}
	
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	public String getUnidadeEnsino() {
		return unidadeEnsino;
	}
	
	public void setUnidadeEnsino(String unidadeEnsino) {
		this.unidadeEnsino = unidadeEnsino;
	}
	
	public String getAluno() {
		return aluno;
	}
	
	public void setAluno(String aluno) {
		this.aluno = aluno;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getSerie() {
		return serie;
	}
	
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
