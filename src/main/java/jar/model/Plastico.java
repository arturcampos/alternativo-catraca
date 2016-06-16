package jar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the plastico database table.
 *
 */
@Entity
@Table(name = "Plastico", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Plastico.findAll", query = "SELECT p FROM Plastico p"),
		@NamedQuery(name = "Plastico.findByDigitableLine", query = "SELECT p FROM Plastico p WHERE p.linhadigitavel = :wantedDigitableLine") })
public class Plastico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datacadastro;

	private String linhadigitavel;

	private String status;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Plastico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatacadastro() {
		return this.datacadastro;
	}

	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}

	public String getLinhadigitavel() {
		return this.linhadigitavel;
	}

	public void setLinhadigitavel(String linhadigitavel) {
		this.linhadigitavel = linhadigitavel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}