package br.gov.ma.tce.reunire.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="reunire", name="tipo_relatorio")
@SequenceGenerator(name = "se_tipo_relatorio", sequenceName = "se_tipo_relatorio", allocationSize = 1)
public class TipoRelatorio implements Serializable {
	
	private static final long serialVersionUID = 6115024177358859427L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="se_tipo_relatorio")
	@Column(name="id_tipo_relatorio", columnDefinition="int(4)")
	private int id;
	
	@Column(name="codigo", length=20)
	private String codigo;
	
	@Column(name="descricao", length=100)
	private String descricao;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}