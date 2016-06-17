package jar.dao;

import jar.model.Evento;
import jar.util.JpaUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class EventoDao implements IDao<Evento> {

	static Logger logger = Logger.getLogger(EventoDao.class);
	private Connection dbConnection;

	/**
	 *
	 */
	public EventoDao() {
		synchronized (PlasticoDao.class) {
			if (dbConnection == null) {
				dbConnection = JpaUtil.getDBConnection();
			}
		}
	}
/**
 * 
 * @param id
 * @param date
 * @return
 * @throws SQLException
 */
	public List<Evento> findEventByPersonAndDate(Long id, Date date) throws SQLException {
		String selectSQL = "SELECT e FROM Evento e "
						+ "WHERE e.pessoa.id = ? AND "
						+ "date_format(datahoraentrada, 'dd/MM/yyyy') = date_format(?, 'dd/MM/yyyy')"
						+ " and datahorasaida IS NULL";
		
		PreparedStatement ps = null;
		List<Evento> eventos = new ArrayList<Evento>();
		
		try {
			ps = dbConnection.prepareStatement(selectSQL);
			ps.setLong(1, (Long) id);
			ResultSet rs = ps.executeQuery();
			Evento evento = null;
			
			while (rs.next()) {
				evento = new Evento();
				evento.setId(rs.getLong("id"));
				evento.setDataHoraEntrada(rs.getTimestamp("datahoraentrada"));
				evento.setDataHoraSaida(rs.getTimestamp("datahorasaida"));
				evento.setPessoaId(rs.getLong("Pessoa_id"));
				evento.setStatus(rs.getString("status"));
				
				eventos.add(evento);
			}
		} catch (SQLException e) {
			logger.error("Erro ao buscar eventos:\n", e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return null;
	}

	@Override
	public void save(Evento evn) throws SQLException {
		String insertSQL = "INSERT INTO Evento(datahoraentrada, datahorasaida, Pessoa_id, status) "
						+ "VALUES(?,?,?,?)";
		PreparedStatement ps = null;

		try {
			ps = dbConnection.prepareStatement(insertSQL);
			ps.setTimestamp(1, (Timestamp) evn.getDataHoraEntrada());
			ps.setTimestamp(2, (Timestamp) evn.getDataHoraSaida());
			ps.setLong(3, evn.getPessoaId());
			ps.setString(4, evn.getStatus());

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Erro ao salvar evento:\n", e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}

	}

	@Override
	public Evento remove(Serializable id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Evento> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Evento findById(Serializable id) throws SQLException {
		String selectSQL = "SELECT e.id, e.datahoraentrada, e.datahorasaida, e.Pessoa_id, e.status"
							+ " FROM Evento e WHERE e.id = ?";
		PreparedStatement ps = null;
		Evento evento = null;
		try {
			ps = dbConnection.prepareStatement(selectSQL);
			ps.setLong(1, (Long) id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				evento = new Evento();
				evento.setId(rs.getLong("id"));
				evento.setDataHoraEntrada(rs.getTimestamp("datahoraentrada"));
				evento.setDataHoraSaida(rs.getTimestamp("datahorasaida"));
				evento.setPessoaId(rs.getLong("Pessoa_id"));
				evento.setStatus(rs.getString("status"));
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
		return evento;
	}

	@Override
	public void update(Evento evn) throws SQLException {
		String updateSQL = "UPDATE Evento e" + "SET e.datahorasaida = ?, e.status = ?" 
							+ "WHERE e.id = ?";
		PreparedStatement ps = null;

		try {
			ps = dbConnection.prepareStatement(updateSQL);
			ps.setTimestamp(1, (Timestamp) evn.getDataHoraSaida());
			ps.setString(2, evn.getStatus());
			ps.setLong(3, evn.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Erro ao atualizar evento:\n", e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}

	}
}
