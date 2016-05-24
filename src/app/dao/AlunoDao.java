package app.dao;

import java.util.List;

import app.model.Aluno;

public class AlunoDao extends DaoImpl<Aluno> {

	/**
	 * 
	 * @param clazz
	 */
	public AlunoDao(Class<Aluno> clazz) {
		super(clazz);
	}


	public List<Aluno> findByPersonId(Long id) throws Exception{
		return (List<Aluno>) entitymanager.createNamedQuery("Aluno.findByPersonId", Aluno.class)
				.setParameter("personId", id).getResultList();
	}
}
