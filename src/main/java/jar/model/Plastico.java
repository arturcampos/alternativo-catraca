package jar.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the plastico database table.
 *
 */

public class Plastico implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataCadastro;
	private String linhaDigitavel;
	private String status;
	private Long pessoaId;

	public Plastico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getLinhaDigitavel() {
		return this.linhaDigitavel;
	}

	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPessoaId() {
		return this.pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

}