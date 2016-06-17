package jar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the pessoa database table.
 *
 */
@Entity
@Table(name = "pessoa", schema = "futurodb")
@NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataNasc;

	private String email;

	private String estadoCivil;

	private String etnia;

	private String nacionalidade;

	private String naturalidade;

	private String necessidadesEspeciais;

	private String nome;

	private String nomeMae;

	private String nomePai;

	private String numeroCelular;

	private String responsavelLegal;

	private String sexo;

	private String uf;

	// bi-directional many-to-one association to Documento
	@OneToMany(mappedBy = "pessoa", cascade=CascadeType.ALL)
	private List<Documento> documentos;

	// bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy = "pessoa", cascade=CascadeType.ALL)
	private List<Endereco> enderecos;

	// bi-directional many-to-one association to Plastico
	@OneToMany(mappedBy = "pessoa", cascade=CascadeType.ALL)
	private List<Plastico> plasticos;

	private String tipopessoa;

	public Pessoa() {
	}

	/**
	 * @param id
	 * @param dataNasc
	 * @param email
	 * @param estadoCivil
	 * @param etnia
	 * @param nacionalidade
	 * @param naturalidade
	 * @param necessidadesEspeciais
	 * @param nome
	 * @param nomeMae
	 * @param nomePai
	 * @param numeroCelular
	 * @param responsavelLegal
	 * @param sexo
	 * @param uf
	 * @param documentos
	 * @param enderecos
	 * @param eventos
	 * @param plasticos
	 * @param tipopessoa
	 */
	public Pessoa(Long id, Date dataNasc, String email, String estadoCivil, String etnia, String nacionalidade,
			String naturalidade, String necessidadesEspeciais, String nome, String nomeMae, String nomePai,
			String numeroCelular, String responsavelLegal, String sexo, String uf, List<Documento> documentos,
			List<Endereco> enderecos, List<Plastico> plasticos, String tipopessoa) {
		super();
		this.id = id;
		this.dataNasc = dataNasc;
		this.email = email;
		this.estadoCivil = estadoCivil;
		this.etnia = etnia;
		this.nacionalidade = nacionalidade;
		this.naturalidade = naturalidade;
		this.necessidadesEspeciais = necessidadesEspeciais;
		this.nome = nome;
		this.nomeMae = nomeMae;
		this.nomePai = nomePai;
		this.numeroCelular = numeroCelular;
		this.responsavelLegal = responsavelLegal;
		this.sexo = sexo;
		this.uf = uf;
		this.documentos = documentos;
		this.enderecos = enderecos;
		this.plasticos = plasticos;
		this.tipopessoa = tipopessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatanasc() {
		return this.dataNasc;

	}

	public void setDatanasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadocivil() {
		return this.estadoCivil;
	}

	public void setEstadocivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEtnia() {
		return this.etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public String getNacionalidade() {
		return this.nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return this.naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNecessidadesespeciais() {
		return this.necessidadesEspeciais;
	}

	public void setNecessidadesespeciais(String necessidadesEspeciais) {
		this.necessidadesEspeciais = necessidadesEspeciais;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomemae() {
		return this.nomeMae;
	}

	public void setNomemae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomepai() {
		return this.nomePai;
	}

	public void setNomepai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNumerocelular() {
		return this.numeroCelular;
	}

	public void setNumerocelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getResponsavellegal() {
		return this.responsavelLegal;
	}

	public void setResponsavellegal(String responsavelLegal) {
		this.responsavelLegal = responsavelLegal;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setPessoa(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setPessoa(null);

		return documento;
	}

	public List<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco addEndereco(Endereco endereco) {
		getEnderecos().add(endereco);
		endereco.setPessoa(this);

		return endereco;
	}

	public Endereco removeEndereco(Endereco endereco) {
		getEnderecos().remove(endereco);
		endereco.setPessoa(null);

		return endereco;
	}

	public List<Plastico> getPlasticos() {
		return this.plasticos;
	}

	public void setPlasticos(List<Plastico> plasticos) {
		this.plasticos = plasticos;
	}

	public Plastico addPlastico(Plastico plastico) {
		getPlasticos().add(plastico);
		plastico.setPessoa(this);

		return plastico;
	}

	public Plastico removePlastico(Plastico plastico) {
		getPlasticos().remove(plastico);
		plastico.setPessoa(null);

		return plastico;
	}

	public String getTipopessoa() {
		return this.tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	@Override
	public Pessoa clone() {
		return new Pessoa(this.id, this.dataNasc, this.email, this.estadoCivil, this.etnia, this.nacionalidade,
				this.naturalidade, this.necessidadesEspeciais, this.nome, this.nomeMae, this.nomePai,
				this.numeroCelular, this.responsavelLegal, this.sexo, this.uf, this.documentos, this.enderecos,
				this.plasticos, this.tipopessoa);
	}

	public void restaurar(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.dataNasc = pessoa.getDatanasc();
		this.email = pessoa.getEmail();
		this.estadoCivil = pessoa.getEstadocivil();
		this.etnia = pessoa.getEtnia();
		this.nacionalidade = pessoa.getNacionalidade();
		this.naturalidade = pessoa.getNaturalidade();
		this.necessidadesEspeciais = pessoa.getNecessidadesespeciais();
		this.nome = pessoa.getNome();
		this.nomeMae = pessoa.getNomemae();
		this.nomePai = pessoa.getNomepai();
		this.numeroCelular = pessoa.getNumerocelular();
		this.responsavelLegal = pessoa.getResponsavellegal();
		this.sexo = pessoa.getSexo();
		this.uf = pessoa.getUf();
		this.documentos = pessoa.getDocumentos();
		this.enderecos = pessoa.getEnderecos();
		this.plasticos = pessoa.getPlasticos();
		this.tipopessoa = pessoa.getTipopessoa();
	}

	public String toString() {
		return new String("id = " + this.id + "\n" + "dataNasc = " + this.dataNasc + "\n" + "email = " + this.email
				+ "\n" + "estadoCivil = " + this.estadoCivil + "\n" + "etnia = " + this.etnia + "\n"
				+ "nacionalidade = " + this.nacionalidade + "\n" + "naturalidade = " + this.naturalidade + "\n"
				+ "necessidadesEspeciais = " + this.necessidadesEspeciais + "\n" + "nome = " + this.nome + "\n"
				+ "nomeMae = " + this.nomeMae + "\n" + "nomePai = " + this.nomePai + "\n" + "numeroCelular = "
				+ this.numeroCelular + "\n" + "responsavelLegal = " + this.responsavelLegal + "\n" + "sexo = "
				+ this.sexo + "\n" + "uf = " + this.uf + "\n" + "documentos = " + this.documentos + "\n"
				+ "enderecos = " + this.enderecos + "\n" + "plasticos = " + this.plasticos + "\n" + "tipopessoa = "
				+ this.tipopessoa + "\n"

		);
	}
}
