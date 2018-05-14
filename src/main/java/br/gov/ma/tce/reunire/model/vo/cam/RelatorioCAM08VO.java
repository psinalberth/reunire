package br.gov.ma.tce.reunire.model.vo.cam;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM08VO extends DemonstrativoVO {
	
	private String unidadeEnsino;
	private String endereco;
	private Integer capacidadeAlunosPreEscolar;
	private Integer capacidadeAlunosFundamental;
	private Integer capacidadeAlunosEnsinoMedio;
	private Integer numeroSalaDeAula;
	private Integer numeroLaboratorio;
	private Integer numeroBiblioteca;
	private Integer numeroQuadraEsporte;
	private Integer numeroCreches;
	
	public String getUnidadeEnsino() {
		return unidadeEnsino;
	}
	public void setUnidadeEnsino(String unidadeEnsino) {
		this.unidadeEnsino = unidadeEnsino;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Integer getCapacidadeAlunosPreEscolar() {
		return capacidadeAlunosPreEscolar;
	}
	public void setCapacidadeAlunosPreEscolar(Integer capacidadeAlunosPreEscolar) {
		this.capacidadeAlunosPreEscolar = capacidadeAlunosPreEscolar;
	}
	public Integer getCapacidadeAlunosFundamental() {
		return capacidadeAlunosFundamental;
	}
	public void setCapacidadeAlunosFundamental(Integer capacidadeAlunosFundamental) {
		this.capacidadeAlunosFundamental = capacidadeAlunosFundamental;
	}
	public Integer getCapacidadeAlunosEnsinoMedio() {
		return capacidadeAlunosEnsinoMedio;
	}
	public void setCapacidadeAlunosEnsinoMedio(Integer capacidadeAlunosEnsinoMedio) {
		this.capacidadeAlunosEnsinoMedio = capacidadeAlunosEnsinoMedio;
	}
	public Integer getNumeroSalaDeAula() {
		return numeroSalaDeAula;
	}
	public void setNumeroSalaDeAula(Integer numeroSalaDeAula) {
		this.numeroSalaDeAula = numeroSalaDeAula;
	}
	public Integer getNumeroLaboratorio() {
		return numeroLaboratorio;
	}
	public void setNumeroLaboratorio(Integer numeroLaboratorio) {
		this.numeroLaboratorio = numeroLaboratorio;
	}
	public Integer getNumeroBiblioteca() {
		return numeroBiblioteca;
	}
	public void setNumeroBiblioteca(Integer numeroBiblioteca) {
		this.numeroBiblioteca = numeroBiblioteca;
	}
	public Integer getNumeroQuadraEsporte() {
		return numeroQuadraEsporte;
	}
	public void setNumeroQuadraEsporte(Integer numeroQuadraEsporte) {
		this.numeroQuadraEsporte = numeroQuadraEsporte;
	}
	
	public Integer getNumeroCreches() {
		return numeroCreches;
	}
	
	public void setNumeroCreches(Integer numeroCreches) {
		this.numeroCreches = numeroCreches;
	}
}
