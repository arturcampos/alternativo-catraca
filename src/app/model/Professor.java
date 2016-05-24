package app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the professor database table.
 * 
 */
@Entity
@Table(name="Professor", schema="futurodb")
@NamedQuery(name="Professor.findAll", query="SELECT p FROM Professor p")
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String formacao;

	private String nivelFormacao;

	//bi-directional one-to-one association to Pessoa
	@OneToOne(cascade=CascadeType.PERSIST)
	private Pessoa pessoa;

	//bi-directional many-to-many association to Disciplina
	@ManyToMany(mappedBy="professors")
	private List<Disciplina> disciplinas;

	//bi-directional many-to-one association to Turma
	@OneToMany(mappedBy="professor")
	private List<Turma> turmas;

	public Professor() {
	}
	
	

	/**
	 * @param id
	 * @param formacao
	 * @param nivelFormacao
	 * @param pessoa
	 * @param disciplinas
	 * @param turmas
	 */
	public Professor(Long id, String formacao, String nivelFormacao, Pessoa pessoa, List<Disciplina> disciplinas,
			List<Turma> turmas) {
		super();
		this.id = id;
		this.formacao = formacao;
		this.nivelFormacao = nivelFormacao;
		this.pessoa = pessoa;
		this.disciplinas = disciplinas;
		this.turmas = turmas;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormacao() {
		return this.formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getNivelformacao() {
		return this.nivelFormacao;
	}

	public void setNivelformacao(String nivelFormacao) {
		this.nivelFormacao = nivelFormacao;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<Turma> getTurmas() {
		return this.turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma addTurma(Turma turma) {
		getTurmas().add(turma);
		turma.setProfessor(this);

		return turma;
	}

	public Turma removeTurma(Turma turma) {
		getTurmas().remove(turma);
		turma.setProfessor(null);

		return turma;
	}
	
	public void restaurar(Professor professor) {
		this.id = professor.getId();
		this.disciplinas = professor.getDisciplinas();
		this.formacao = professor.getFormacao();
		this.nivelFormacao = professor.getNivelformacao();
		this.pessoa = professor.getPessoa();
		this.turmas = professor.getTurmas();
		
	}
	
	@Override
	public Professor clone(){
		return new Professor(this.id, this.formacao, this.nivelFormacao, this.pessoa, this.disciplinas, this.turmas);
	}

}