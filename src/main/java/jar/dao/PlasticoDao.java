package jar.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import jar.model.Plastico;

public class PlasticoDao extends DaoImpl<Plastico> {

	public PlasticoDao(Class<Plastico> clazz) {
		super(clazz);
	}

	public Plastico findByDigitableLine(String digitableLine){
		Query query = entitymanager.createNamedQuery("Plastico.findByDigitableLine")
				.setParameter("wantedDigitableLine", digitableLine);
		try{
			Plastico p = (Plastico) query.getSingleResult();
			return p;
		}catch(NoResultException e){
			System.err.println(e.getMessage());
			return null;
		}


	}

}
