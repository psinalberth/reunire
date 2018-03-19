package br.gov.ma.tce.reunire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_RELATORIO")
public class TipoRelatorio extends EntidadeBase {
	
	private static final long serialVersionUID = 6115024177358859427L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_RELATORIO", columnDefinition="TINYINT(3)")
	private int id;
	
	@Column(name="CODIGO", length=20)
	private String codigo;
	
	@Column(name="NOME", length=100)
	private String nome;
	
	@Column(name="CLASSE", length=45)
	private String classe;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
}