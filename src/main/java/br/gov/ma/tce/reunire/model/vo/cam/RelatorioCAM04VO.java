package br.gov.ma.tce.reunire.model.vo.cam;

public class RelatorioCAM04VO {
	
	private int idUnidade;
	private String descricaoUnidade;
	private String nomePovoado;
	private String formaAcesso;
	private Double distancia;

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

	public String getNomePovoado() {
		return nomePovoado;
	}

	public void setNomePovoado(String nomePovoado) {
		this.nomePovoado = nomePovoado;
	}

	public String getFormaAcesso() {
		return formaAcesso;
	}

	public void setFormaAcesso(String formaAcesso) {
		this.formaAcesso = formaAcesso;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
}