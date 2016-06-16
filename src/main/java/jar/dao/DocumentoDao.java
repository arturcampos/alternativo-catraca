package jar.dao;

import java.util.List;

import jar.model.Documento;

public class DocumentoDao extends DaoImpl<Documento> {

	public DocumentoDao(Class<Documento> clazz) {
		super(clazz);
	}

	public List<Documento> findByPersonId(Long personId){
		return entitymanager.createNamedQuery("Documento.findByPersonId")
				.setParameter("personId", personId).getResultList();
	}



}
