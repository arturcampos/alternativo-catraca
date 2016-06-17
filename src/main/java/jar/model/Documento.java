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
 * The persistent class for the documento database table.
 *
 */
@Entity
@Table(name = "Documento", schema = "futurodb")

@NamedQueries({
	@NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
	@NamedQuery(name = "Documento.findByPersonId", query = "SELECT d FROM Documento d WHERE d.pessoa.id = :personId")
})
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;

	private String tipo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataexpedicao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datavalidade;

	private String orgaoemissor;

	private String uf;

	// bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Documento() {
	}

	public Documento(Long id, String numero, String tipo, Date dataexpedicao, Date datavalidade, String orgaoemissor,
			String uf, Pessoa pessoa) {
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.dataexpedicao = dataexpedicao;
		this.datavalidade = datavalidade;
		this.orgaoemissor = orgaoemissor;
		this.uf = uf;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataexpedicao() {
		return this.dataexpedicao;
	}

	public void setDataexpedicao(Date dataexpedicao) {
		this.dataexpedicao = dataexpedicao;
	}

	public Date getDatavalidade() {
		return this.datavalidade;
	}

	public void setDatavalidade(Date datavalidade) {
		this.datavalidade = datavalidade;
	}

	public String getOrgaoemissor() {
		return this.orgaoemissor;
	}

	public void setOrgaoemissor(String orgaoemissor) {
		this.orgaoemissor = orgaoemissor;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return new String("Número:" + this.numero + "\nOrgão Emissor:" + this.orgaoemissor + "\nData de Emissão:"
				+ this.dataexpedicao + "\nData Validade:" + this.datavalidade);
	}

	@Override
	public Documento clone() {
		return new Documento(this.id, this.numero, this.tipo, this.dataexpedicao, this.datavalidade, this.orgaoemissor,
				this.uf, this.pessoa);
	}

}