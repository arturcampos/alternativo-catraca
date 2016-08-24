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
	}
/**
 *
 * @param id
 * @param date
 * @return
 * @throws SQLException
 */
	public List<Evento> findEventByPersonAndDate(Long id, Date date) throws SQLException {
		String selectSQL = "SELECT e.id, e.datahoraentrada, e.datahorasaida, e.Pessoa_id, e.status FROM evento e "
						+ "WHERE e.Pessoa_id = ? AND "
						+ "date_format(e.datahoraentrada, 'dd/MM/yyyy') = date_format(?, 'dd/MM/yyyy')"
						+ " AND (e.datahorasaida IS NULL OR e.datahorasaida LIKE '')";

		PreparedStatement ps = null;
		List<Evento> eventos = new ArrayList<Evento>();

		try {
			dbConnection = JpaUtil.getDBConnection();
			ps = dbConnection.prepareStatement(selectSQL);
			ps.setLong(1, (Long) id);
			ps.setDate(2, new java.sql.Date(date.getTime()));
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
		return eventos;
	}

	@Override
	public void save(Evento evn) throws SQLException {
		String insertSQL = "INSERT INTO evento(datahoraentrada, datahorasaida, Pessoa_id, status) "
						+ "VALUES(?,?,?,?)";
		PreparedStatement ps = null;

		try {
			dbConnection = JpaUtil.getDBConnection();
			ps = dbConnection.prepareStatement(insertSQL);
			if(evn.getDataHoraEntrada() != null){
				ps.setTimestamp(1, new Timestamp(evn.getDataHoraEntrada().getTime()));
			}
			else{
				ps.setNull(1,java.sql.Types.TIMESTAMP);
			}
			if(evn.getDataHoraSaida() != null){
				ps.setTimestamp(2, new Timestamp(evn.getDataHoraSaida().getTime()));
			}
			else{
				ps.setNull(2,java.sql.Types.TIMESTAMP);
			}
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
							+ " FROM evento e WHERE e.id = ?";
		PreparedStatement ps = null;
		Evento evento = null;
		try {
			dbConnection = JpaUtil.getDBConnection();
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
		String updateSQL = "UPDATE evento e SET e.datahorasaida = ?, e.status = ? "
							+ "WHERE e.id = ?";
		PreparedStatement ps = null;

		try {
			dbConnection = JpaUtil.getDBConnection();
			ps = dbConnection.prepareStatement(updateSQL);
			ps.setTimestamp(1, new Timestamp(evn.getDataHoraSaida().getTime()));
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
