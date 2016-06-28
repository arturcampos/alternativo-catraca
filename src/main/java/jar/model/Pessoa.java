package jar.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the pessoa database table.
 *
 */
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
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
	private String tipoPessoa;

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
			String numeroCelular, String responsavelLegal, String sexo, String uf, String tipopessoa) {
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
		this.tipoPessoa = tipopessoa;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataNasc() {
		return this.dataNasc;

	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
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

	public String getNecessidadesEspeciais() {
		return this.necessidadesEspeciais;
	}

	public void setNecessidadesEspeciais(String necessidadesEspeciais) {
		this.necessidadesEspeciais = necessidadesEspeciais;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeMae() {
		return this.nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return this.nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNumeroCelular() {
		return this.numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getResponsavelLegal() {
		return this.responsavelLegal;
	}

	public void setResponsavelLegal(String responsavelLegal) {
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

	public String getTipoPessoa() {
		return this.tipoPessoa;
	}

	public void setTipoPessoa(String tipopessoa) {
		this.tipoPessoa = tipopessoa;
	}

	@Override
	public Pessoa clone() {
		return new Pessoa(this.id, this.dataNasc, this.email, this.estadoCivil, this.etnia, this.nacionalidade,
				this.naturalidade, this.necessidadesEspeciais, this.nome, this.nomeMae, this.nomePai,
				this.numeroCelular, this.responsavelLegal, this.sexo, this.uf, this.tipoPessoa);
	}

	public void restaurar(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.dataNasc = pessoa.getDataNasc();
		this.email = pessoa.getEmail();
		this.estadoCivil = pessoa.getEstadoCivil();
		this.etnia = pessoa.getEtnia();
		this.nacionalidade = pessoa.getNacionalidade();
		this.naturalidade = pessoa.getNaturalidade();
		this.necessidadesEspeciais = pessoa.getNecessidadesEspeciais();
		this.nome = pessoa.getNome();
		this.nomeMae = pessoa.getNomeMae();
		this.nomePai = pessoa.getNomePai();
		this.numeroCelular = pessoa.getNumeroCelular();
		this.responsavelLegal = pessoa.getResponsavelLegal();
		this.sexo = pessoa.getSexo();
		this.uf = pessoa.getUf();
		this.tipoPessoa = pessoa.getTipoPessoa();
	}

	public String toString() {
		return new String("id = " + this.id + "\n" + "dataNasc = " + this.dataNasc + "\n" + "email = " + this.email
				+ "\n" + "estadoCivil = " + this.estadoCivil + "\n" + "etnia = " + this.etnia + "\n"
				+ "nacionalidade = " + this.nacionalidade + "\n" + "naturalidade = " + this.naturalidade + "\n"
				+ "necessidadesEspeciais = " + this.necessidadesEspeciais + "\n" + "nome = " + this.nome + "\n"
				+ "nomeMae = " + this.nomeMae + "\n" + "nomePai = " + this.nomePai + "\n" + "numeroCelular = "
				+ this.numeroCelular + "\n" + "responsavelLegal = " + this.responsavelLegal + "\n" + "sexo = "
				+ this.sexo + "\n" + "uf = " + this.uf + "\n" + "tipopessoa = "
				+ this.tipoPessoa + "\n"

		);
	}
}
