/**
 * 
 */
package app.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import app.util.JpaUtil;

/**
 * @author artur.rodrigues
 *
 */
public class DaoImpl<T> implements IDao<T> {

	protected EntityManager entitymanager;
	protected Class<T> classe;

	/**
	 * 
	 * @param clazz
	 */
	public DaoImpl(Class<T> clazz) {
		this.classe = clazz;

		synchronized (DaoImpl.class) {
			if (entitymanager == null) {
				entitymanager = JpaUtil.getEntityManager();
			}
		}
	}

	/**
	 * @param obj - Objeto do tipo genérico
	 */
	public void save(T obj) {
		EntityTransaction tx = entitymanager.getTransaction();
		tx.begin();
		try {
			entitymanager.persist(obj);
		} finally {
			tx.commit();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public T remove(Serializable id) {
		T obj = findById(id);
		if (obj != null) {
			EntityTransaction tx = entitymanager.getTransaction();
			tx.begin();
			try {
				entitymanager.remove(obj);
			} finally {
				tx.commit();
			}
		}
		return obj;
	}

	/**
	 * @param id
	 * @return
	 */
	public T findById(Serializable id) {
		T obj = (T) entitymanager.find(this.classe, id);
		return obj;
	}

	/**
	 * @return
	 */
	public List<T> findAll() {
		TypedQuery<T> query = entitymanager.createNamedQuery(classe.getSimpleName() + ".findAll", this.classe);
		List<T> results = query.getResultList();

		return results;

	}
	
	/**
	 * @param obj - objeto tipo genérico
	 */
	
	public void update(T obj) {
		EntityTransaction tx = entitymanager.getTransaction();
		tx.begin();
		try {
			entitymanager.merge(obj);
		} finally {
			tx.commit();
		}
	}

}
