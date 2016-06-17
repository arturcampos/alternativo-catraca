/**
 *
 */
package jar.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author artur.rodrigues
 *
 */
public interface IDao<T> {

	public void save(T obj) throws SQLException;
	public T remove(Serializable id) throws SQLException;
	public List<T> findAll() throws SQLException;
	public T findById(Serializable id) throws SQLException;
	public void update(T obj) throws SQLException;

}
