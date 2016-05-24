package app.dao;

import app.model.Pessoa;

public class PessoaDao extends DaoImpl<Pessoa> {

	public PessoaDao(Class<Pessoa> clazz) {
		super(clazz);
	}
}
