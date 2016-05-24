/**
 * 
 */
package app.dao;

import java.io.Serializable;   
import java.util.List;

/**
 * @author artur.rodrigues
 *
 */
public interface IDao<T> {

	public void save(T obj);
	public T remove(Serializable id);
	public List<T> findAll();
	public T findById(Serializable id);
	public void update(T obj);
	
}
