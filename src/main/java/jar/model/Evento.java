package jar.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
 * The persistent class for the evento database table.
 *
 */
@Entity
@Table(name = "Evento", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
		@NamedQuery(name = "Evento.findEventByPersonAndDate", query = "SELECT e FROM Evento e WHERE e.pessoa.id = :personId and date_format(datahoraentrada, 'dd/MM/yyyy') = date_format(:date, 'dd/MM/yyyy') and datahorasaida IS NULL")})
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datahoraentrada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datahorasaida;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	private String status;

	public Evento() {
	}

	public Evento(Date entrada, Date saida, String status, Pessoa pessoa) {
		this.datahoraentrada = entrada;
		this.datahorasaida = saida;
		this.status = status;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatahoraentrada() {
		return this.datahoraentrada;
	}

	public void setDatahoraentrada(Date datahoraentrada) {
		this.datahoraentrada = datahoraentrada;
	}

	public Date getDatahorasaida() {
		return this.datahorasaida;
	}

	public void setDatahorasaida(Date datahorasaida) {
		this.datahorasaida = datahorasaida;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}