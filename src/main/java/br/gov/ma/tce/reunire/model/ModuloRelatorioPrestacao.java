package br.gov.ma.tce.reunire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="prestacao", name="modulo")
public class ModuloRelatorioPrestacao {
	
	@Id
	@Column(name="modulo_id")
	private int id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="quantidade_pecas")
	private int quantidadePecas;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getQuantidadePecas() {
		return quantidadePecas;
	}
	
	public void setQuantidadePecas(int quantidadePecas) {
		this.quantidadePecas = quantidadePecas;
	}
}