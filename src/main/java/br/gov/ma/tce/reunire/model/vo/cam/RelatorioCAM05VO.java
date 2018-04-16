package br.gov.ma.tce.reunire.model.vo.cam;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM05VO extends DemonstrativoVO {
	
	private int id;
	private String orgao;
	private String finalidade;
	private String cpfCnpjProprietario;
	private String nomeProprietario;
	private String modeloVeiculo;
	private String plavaDoVeiculo;
	private String renavam;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	
	public String getCpfCnpjProprietario() {
		return cpfCnpjProprietario;
	}
	public void setCpfCnpjProprietario(String cpfCnpjProprietario) {
		this.cpfCnpjProprietario = cpfCnpjProprietario;
	}
	
	public String getNomeProprietario() {
		return nomeProprietario;
	}
	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}
	
	public String getModeloVeiculo() {
		return modeloVeiculo;
	}
	public void setModeloVeiculo(String modeloVeiculo) {
		this.modeloVeiculo = modeloVeiculo;
	}
	
	public String getPlavaDoVeiculo() {
		return plavaDoVeiculo;
	}
	public void setPlavaDoVeiculo(String plavaDoVeiculo) {
		this.plavaDoVeiculo = plavaDoVeiculo;
	}
	
	public String getRenavam() {
		return renavam;
	}
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	
	
	
}
