package jar.dao;

import java.util.List;

import jar.model.Aluno;

public class AlunoDao extends DaoImpl<Aluno> {

	/**
	 *
	 * @param clazz
	 */
	public AlunoDao(Class<Aluno> clazz) {
		super(clazz);
	}


	public List<Aluno> findByPersonId(Long id) throws Exception{
		return (List<Aluno>) entitymanager.createNamedQuery("Aluno.findByPersonId")
				.setParameter("wantedPersonId", id).getResultList();
	}
}
