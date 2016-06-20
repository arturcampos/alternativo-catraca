package jar.dao;

import jar.model.Pessoa;
import jar.util.JpaUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class PessoaDao implements IDao<Pessoa> {

	static Logger logger = Logger.getLogger(PessoaDao.class);
	private Connection dbConnection;

	/**
	 *
	 */
	public PessoaDao() {
	}

	@Override
	public void save(Pessoa obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Pessoa remove(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pessoa> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pessoa findById(Serializable id) throws SQLException {
		String selectSQL = "SELECT p.id, p.nome, p.datanasc, p.sexo, p.naturalidade, p.uf, p.nomepai, "
						+ "p.nomemae, p.responsavellegal, p.email, p.numerocelular, p.necessidadesespeciais, "
						+ "p.etnia, p.nacionalidade, p.estadocivil, p.tipopessoa"
						+ " FROM Pessoa p WHERE p.id = ?";
	PreparedStatement ps = null;
	Pessoa pessoa = null;
	try {
		dbConnection = JpaUtil.getDBConnection();
		ps = dbConnection.prepareStatement(selectSQL);
		ps.setLong(1, (Long) id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			pessoa = new Pessoa();
			pessoa.setId(rs.getLong("id"));
			pessoa.setNome(rs.getString("nome"));
			pessoa.setDataNasc(rs.getTimestamp("datanasc"));
			pessoa.setSexo(rs.getString("sexo"));
			pessoa.setNaturalidade(rs.getString("naturalidade"));
			pessoa.setUf(rs.getString("uf"));
			pessoa.setNomePai(rs.getString("nomepai"));
			pessoa.setNomeMae(rs.getString("nomemae"));
			pessoa.setResponsavelLegal(rs.getString("responsavellegal"));
			pessoa.setEmail(rs.getString("email"));
			pessoa.setNumeroCelular(rs.getString("numerocelular"));
			pessoa.setNecessidadesEspeciais(rs.getString("necessidadesespeciais"));
			pessoa.setEtnia(rs.getString("etnia"));
			pessoa.setNacionalidade(rs.getString("nacionalidade"));
			pessoa.setEstadoCivil(rs.getString("estadocivil"));
			pessoa.setTipoPessoa(rs.getString("tipopessoa"));

		}
	} catch (SQLException e) {
		logger.error("Erro ao buscar evento:\n", e);
	} finally {
		if (ps != null) {
			ps.close();
		}
		if (dbConnection != null) {
			dbConnection.close();
		}
	}
	return pessoa;
}

	@Override
	public void update(Pessoa obj) throws SQLException {
		// TODO Auto-generated method stub

	}
}
