package br.gov.ma.tce.reunire.model.vo.cam;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM10VO extends DemonstrativoVO {
	
	private String tipo;
	private String nome;
	private String endereco;
	private Integer numeroMedicos;
	private Integer numeroEnfermeiros;
	private Integer numeroOutros;
	private Integer atendimentos;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumeroMedicos() {
		return numeroMedicos;
	}

	public void setNumeroMedicos(Integer numeroMedicos) {
		this.numeroMedicos = numeroMedicos;
	}

	public Integer getNumeroEnfermeiros() {
		return numeroEnfermeiros;
	}

	public void setNumeroEnfermeiros(Integer numeroEnfermeiros) {
		this.numeroEnfermeiros = numeroEnfermeiros;
	}

	public Integer getNumeroOutros() {
		return numeroOutros;
	}

	public void setNumeroOutros(Integer numeroOutros) {
		this.numeroOutros = numeroOutros;
	}

	public Integer getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(Integer atendimentos) {
		this.atendimentos = atendimentos;
	}
}