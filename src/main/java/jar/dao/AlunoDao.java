package jar.dao;

import jar.model.Aluno;
import jar.util.JpaUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class AlunoDao implements IDao<Aluno> {

	static Logger logger = Logger.getLogger(AlunoDao.class);
	private Connection dbConnection; 
	
	/**
	 *
	 */
	public AlunoDao() {
		
		synchronized (AlunoDao.class) {
			if (dbConnection == null) {
				dbConnection = JpaUtil.getDBConnection();
			}
		}
	}


	public List<Aluno> findByPersonId(Long id) throws SQLException{
		
		String selectSQL = "SELECT a.id, a.matricula, a.tipocotaingresso,"
						+ " a.dataingresso, a.dataegresso,"
						+ " a.Turma_id, a.status, a.pessoa_Id"
						+ " FROM Aluno a WHERE a.Pessoa_id = ?";
		PreparedStatement ps = null;
		List<Aluno> alunos = new  ArrayList<Aluno>();
		try {
			ps = dbConnection.prepareStatement(selectSQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getLong("id"));
				aluno.setMatricula(rs.getString("matricula"));
				aluno.setDataIngresso(rs.getDate("dataingresso"));
				aluno.setDataIngresso(rs.getDate("dataegresso"));
				aluno.setPessoaId(rs.getLong("Pessoa_id"));
				aluno.setTurmaId(rs.getLong("Turma_id"));
				aluno.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			logger.error("ERROR: " +e.getMessage());
		}
		finally{
			if (ps != null) {
				ps.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return alunos;
	}


	@Override
	public void save(Aluno obj) throws SQLException{
		// TODO Auto-generated method stub
		
	}


	@Override
	public Aluno remove(Serializable id) throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Aluno> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Aluno findById(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(Aluno obj) throws SQLException{
		// TODO Auto-generated method stub
		
	}
}
