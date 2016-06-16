package jar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the aluno database table.
 *
 */
@Entity
@Table(name = "aluno", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
		@NamedQuery (name="Aluno.findByPersonId", query="SELECT a FROM Aluno a WHERE a.pessoa.id = :wantedPersonId")})
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEgresso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataIngresso;

	private String matricula;

	private int tipoCotaIngresso;

	// bi-directional many-to-one association to Turma
	@ManyToOne
	private Turma turma;

	// bi-directional one-to-one association to Pessoa
	@OneToOne(cascade=CascadeType.PERSIST)
	private Pessoa pessoa;

	private String status;

	public Aluno() {
	}

	public Aluno(Long id, Date dataEgresso, Date dataIngresso, String matricula, int tipoCotaIngresso, Turma turma,
			Pessoa pessoa, String status) {
		super();
		this.id = id;
		this.dataEgresso = dataEgresso;
		this.dataIngresso = dataIngresso;
		this.matricula = matricula;
		this.tipoCotaIngresso = tipoCotaIngresso;
		this.turma = turma;
		this.pessoa = pessoa;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEgresso() {
		return this.dataEgresso;
	}

	public void setDataEgresso(Date dataEgresso) {
		this.dataEgresso = dataEgresso;
	}

	public Date getDataIngresso() {
		return this.dataIngresso;
	}

	public void setDataIngresso(Date dataIngresso) {
		this.dataIngresso = dataIngresso;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getTipoCotaIngresso() {
		return this.tipoCotaIngresso;
	}

	public void setTipoCotaIngresso(int tipoCotaIngresso) {
		this.tipoCotaIngresso = tipoCotaIngresso;
	}

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public Aluno clone() {
		return new Aluno(id, dataEgresso, dataIngresso, matricula, tipoCotaIngresso, turma, pessoa, status);
	}

	public void restaurar(Aluno aluno) {
		this.id = aluno.getId();
		this.dataEgresso = aluno.getDataEgresso();
		this.dataIngresso = aluno.getDataIngresso();
		this.matricula = aluno.getMatricula();
		this.tipoCotaIngresso = aluno.getTipoCotaIngresso();
		this.turma = aluno.getTurma();
		this.pessoa = aluno.getPessoa();
		this.status = aluno.getStatus();
	}

	@Override
	public String toString() {
		return new String(this.id + "\nData Egresso: " + this.dataEgresso + "\nData Ingresso: " + this.dataIngresso
				+ "\nMatricula: " + this.matricula + "\n Tipo de Cota: " + this.tipoCotaIngresso + "\nStatus:"
				+ this.status);
	}
}