package br.gov.ma.tce.reunire.model.vo.cam;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM21VO extends DemonstrativoVO {

	private String deliberacao;
	private Integer exercicio;
	private String recomendacao;
	private String situacao;
	private String providencias;
	private String justificativa;

	public String getDeliberacao() {
		return deliberacao;
	}

	public void setDeliberacao(String deliberacao) {
		this.deliberacao = deliberacao;
	}

	public Integer getExercicio() {
		return exercicio;
	}

	public void setExercicio(Integer exercicio) {
		this.exercicio = exercicio;
	}

	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getProvidencias() {
		return providencias;
	}

	public void setProvidencias(String providencias) {
		this.providencias = providencias;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
}