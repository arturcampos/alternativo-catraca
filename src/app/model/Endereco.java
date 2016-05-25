package app.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the endereco database table.
 * 
 */
@Entity
@Table(name = "Endereco", schema = "futurodb")
@NamedQueries({ 
	@NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
	@NamedQuery(name = "Endereco.findByPersonId", query = "SELECT e FROM Endereco e WHERE e.pessoa.id = :personId")
})

public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String bairro;

	private String cep;

	private String cidade;

	private String enderecocorrespondencia;

	private String logradouro;

	private int numero;

	private String telefone;

	private String tipo;

	private String uf;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Endereco() {
	}

	/**
	 * @param id
	 * @param bairro
	 * @param cep
	 * @param cidade
	 * @param enderecocorrespondencia
	 * @param logradouro
	 * @param numero
	 * @param telefone
	 * @param tipo
	 * @param uf
	 * @param pessoa
	 */
	public Endereco(Long id, String bairro, String cep, String cidade, String enderecocorrespondencia,
			String logradouro, int numero, String telefone, String tipo, String uf, Pessoa pessoa) {
		super();
		this.id = id;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.enderecocorrespondencia = enderecocorrespondencia;
		this.logradouro = logradouro;
		this.numero = numero;
		this.telefone = telefone;
		this.tipo = tipo;
		this.uf = uf;
		this.pessoa = pessoa;
	}

	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getBairro() {
		return this.bairro;
	}

	/**
	 * 
	 * @param bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * 
	 * @return
	 */
	public String getCep() {
		return this.cep;
	}

	/**
	 * 
	 * @param cep
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * 
	 * @return
	 */
	public String getCidade() {
		return this.cidade;
	}

	/**
	 * 
	 * @param cidade
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * 
	 * @return
	 */
	public String getEnderecocorrespondencia() {
		return this.enderecocorrespondencia;
	}

	/**
	 * 
	 * @param enderecocorrespondencia
	 */
	public void setEnderecocorrespondencia(String enderecocorrespondencia) {
		this.enderecocorrespondencia = enderecocorrespondencia;
	}

	/**
	 * 
	 * @return
	 */
	public String getLogradouro() {
		return this.logradouro;
	}

	/**
	 * 
	 * @param logradouro
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumero() {
		return this.numero;
	}

	/**
	 * 
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * 
	 * @return
	 */
	public String getTelefone() {
		return this.telefone;
	}

	/**
	 * 
	 * @param telefone
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return this.tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getUf() {
		return this.uf;
	}

	/**
	 * 
	 * @param uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * 
	 * @return
	 */
	public Pessoa getPessoa() {
		return this.pessoa;
	}

	/**
	 * 
	 * @param pessoa
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return new String("Logradouro: " + this.logradouro + "\nNumero: " + this.numero + "\nBairro: " + this.bairro
				+ "\nCidade: " + this.cidade + "\nCEP: " + this.cep + "\nUF: " + this.uf
				+ "\n Endereço de Correspondência: " + this.enderecocorrespondencia + "\nTelefone:" + this.telefone
				+ "\nTipo de endereço: " + this.tipo);
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Endereco clone() {
		return new Endereco(this.id, this.bairro, this.cep, this.cidade, this.enderecocorrespondencia, this.logradouro,
				this.numero, this.telefone, this.tipo, this.uf, this.pessoa);
	}
}