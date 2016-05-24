package app.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Plastico;

public class PlasticoDao extends DaoImpl<Plastico> {

	public PlasticoDao(Class<Plastico> clazz) {
		super(clazz);
	}
	
	public Plastico findByDigitableLine(String digitableLine){
		Query query = entitymanager.createNamedQuery("Plastico.findByDigitableLine", Plastico.class)
				.setParameter("wantedDigitableLine", digitableLine);
		try{
			Plastico p = (Plastico) query.getSingleResult();
			return p;
		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
		
		
	}

}
