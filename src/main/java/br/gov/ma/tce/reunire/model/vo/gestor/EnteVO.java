package br.gov.ma.tce.reunire.model.vo.gestor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="GESTOR", name="ENTE")
public class EnteVO {
	
	@Id
	@Column(name="ENTE_ID")
	private Integer id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="ESTADO_ID")
	private Integer estadoId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}
	
}
