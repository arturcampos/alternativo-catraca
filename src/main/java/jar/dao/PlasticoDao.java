package jar.dao;

import jar.model.Plastico;
import jar.util.JpaUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class PlasticoDao implements IDao<Plastico> {

	static Logger logger = Logger.getLogger(PlasticoDao.class);
	private Connection dbConnection;

	/**
	 *
	 */
	public PlasticoDao() {
	}

	public Plastico findByDigitableLine(String digitableLine) throws SQLException {
		String selectSQL = "SELECT p.id, p.linhadigitavel, p.datacadastro, p.status, p.Pessoa_id"
						+ " FROM Plastico p WHERE p.linhadigitavel = ?";
		PreparedStatement ps = null;
		Plastico plastico = null;
		try {
			dbConnection = JpaUtil.getDBConnection();
			ps = dbConnection.prepareStatement(selectSQL);
			ps.setString(1, digitableLine);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				plastico = new Plastico();
				plastico.setId(rs.getLong("id"));
				plastico.setLinhaDigitavel(rs.getString("linhadigitavel"));
				plastico.setDataCadastro(rs.getDate("datacadastro"));
				plastico.setPessoaId(rs.getLong("Pessoa_id"));
				plastico.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			logger.error("Erro ao buscar plastico:\n", e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return plastico;
	}

	@Override
	public void save(Plastico obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Plastico remove(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Plastico> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plastico findById(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Plastico obj) throws SQLException {
		// TODO Auto-generated method stub

	}

}
