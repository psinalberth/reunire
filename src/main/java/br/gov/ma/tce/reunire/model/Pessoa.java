package br.gov.ma.tce.reunire.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PESSOA")
public class Pessoa extends EntidadeBase {
	
	private static final long serialVersionUID = 3727409335063560932L;
	
	@Id
	@GeneratedValue
	@Column(name="ID_PESSOA", columnDefinition="TINYINT(3)")
	private int id;
	
	@Column(name="NOME", length=80)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name="ENDERECOS_PESSOA", joinColumns = {@JoinColumn(name="ID_PESSOA")}, inverseJoinColumns = {@JoinColumn(name="ID_ENDERECO")})
	private List<Endereco> enderecos;

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
}