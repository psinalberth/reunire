package br.gov.ma.tce.reunire.model.vo.cam;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM04VO extends DemonstrativoVO {
	
	private String nomePovoado;
	private String formaAcesso;
	private Double distancia;

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