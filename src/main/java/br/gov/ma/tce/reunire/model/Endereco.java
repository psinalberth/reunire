package br.gov.ma.tce.reunire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ENDERECO")
public class Endereco extends EntidadeBase {
	
	private static final long serialVersionUID = 9062378366002509374L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ENDERECO", columnDefinition="TINYINT(3)")
	private int id;
	
	@Column(name="LOGRADOURO", length=100)
	private String logradouro;
	
	@Column(name="BAIRRO", length=100)
	private String bairro;
	
	@Column(name="CIDADE", length=100)
	private String cidade;
	
	@Column(name="UF", length=2)
	private String uf;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}