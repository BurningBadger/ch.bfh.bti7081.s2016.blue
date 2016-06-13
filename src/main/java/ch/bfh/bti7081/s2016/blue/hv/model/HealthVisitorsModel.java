package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;

public class HealthVisitorsModel extends BaseModel<HealthVisitor, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(HealthVisitorsModel.class.getName());

    public HealthVisitorsModel() {
	super(HealthVisitor.class);
    }

    boolean validate(HealthVisitor healthVisitor) {

	return false;
    }

    public HealthVisitor findHealthVisitor(HealthVisitor element) throws EntityNotFoundException {
	getTransaction().begin();

	Entity annotation = HealthVisitor.class.getAnnotation(Entity.class);
	String tableName = annotation.name();

	List<HealthVisitor> users = getEntityManager()
		.createQuery("SELECT hv FROM " + tableName
			+ " hv WHERE hv.userName LIKE :userName AND hv.password LIKE :userPass")
		.setParameter("userName", element.getUserName()).setParameter("userPass", element.getPassword())
		.setMaxResults(1).getResultList();

	getTransaction().commit();

	return users.size() > 0 ? users.get(0) : null;
    }

}
