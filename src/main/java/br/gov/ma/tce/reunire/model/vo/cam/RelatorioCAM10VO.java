package br.gov.ma.tce.reunire.model.vo.cam;

public class RelatorioCAM10VO {
	
	private int idUnidade;
	private String descricaoUnidade;
	private String tipo;
	private String nome;
	private String endereco;
	private Integer numeroMedicos;
	private Integer numeroEnfermeiros;
	private Integer numeroOutros;
	private Integer atendimentos;

	public int getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(int idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade) {
		this.descricaoUnidade = descricaoUnidade;
	}

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