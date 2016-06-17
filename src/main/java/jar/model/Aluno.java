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
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date dataEgresso;

	private Date dataIngresso;

	private String matricula;

	private int tipoCotaIngresso;

	private Long turmaId;

	private Long pessoaId;

	private String status;

	public Aluno() {
	}

	public Aluno(Long id, Date dataEgresso, Date dataIngresso, String matricula, int tipoCotaIngresso, Long turmaId,
			Long pessoaId, String status) {
		super();
		this.id = id;
		this.dataEgresso = dataEgresso;
		this.dataIngresso = dataIngresso;
		this.matricula = matricula;
		this.tipoCotaIngresso = tipoCotaIngresso;
		this.turmaId = turmaId;
		this.pessoaId = pessoaId;
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

	public Long getTurmaId() {
		return this.turmaId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	public Long getPessoaId() {
		return this.pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public Aluno clone() {
		return new Aluno(id, dataEgresso, dataIngresso, matricula, tipoCotaIngresso, turmaId, pessoaId, status);
	}

	public void restaurar(Aluno aluno) {
		this.id = aluno.getId();
		this.dataEgresso = aluno.getDataEgresso();
		this.dataIngresso = aluno.getDataIngresso();
		this.matricula = aluno.getMatricula();
		this.tipoCotaIngresso = aluno.getTipoCotaIngresso();
		this.turmaId = aluno.getTurmaId();
		this.pessoaId = aluno.getPessoaId();
		this.status = aluno.getStatus();
	}

	@Override
	public String toString() {
		return new String(this.id + "\nData Egresso: " + this.dataEgresso + "\nData Ingresso: " + this.dataIngresso
				+ "\nMatricula: " + this.matricula + "\n Tipo de Cota: " + this.tipoCotaIngresso + "\nStatus:"
				+ this.status);
	}
}