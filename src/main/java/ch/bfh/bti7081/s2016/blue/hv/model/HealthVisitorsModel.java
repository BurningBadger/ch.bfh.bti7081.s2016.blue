package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;

import com.vaadin.server.VaadinSession;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;

public class HealthVisitorsModel extends BaseModel<HealthVisitor, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(HealthVisitorsModel.class.getName());

    public HealthVisitorsModel() {
	super(HealthVisitor.class);
    }

    boolean validate(HealthVisitor healthVisitor) {

	return false;
    }

    /**
     * 
     * @param element
     * @return
     * @throws EntityNotFoundException
     */
    public HealthVisitor findHealthVisitor(HealthVisitor element) throws EntityNotFoundException {
	getTransaction().begin();

	Entity annotation = HealthVisitor.class.getAnnotation(Entity.class);
	String tableName = annotation.name();

	@SuppressWarnings("unchecked")
	List<HealthVisitor> users = getEntityManager()
		.createQuery(
			"SELECT hv FROM " + tableName + " hv WHERE hv.userName = :userName AND hv.password = :userPass")
		.setParameter("userName", element.getUserName()).setParameter("userPass", element.getPassword())
		.setMaxResults(1).getResultList();

	getTransaction().commit();

	return users.size() > 0 ? users.get(0) : null;
    }

    /**
     * Find the currently logged in {@link HealthVisitor}.
     * 
     * @return the {@link HealthVisitor} or null if none exists in the
     *         {@link VaadinSession}.
     */
    public HealthVisitor findCurrentHealthVisitor() {
	VaadinSession session = VaadinSession.getCurrent();
	Object user = session.getAttribute("userId");
	if (user == null) {
	    return null;
	}
	return this.findById(Long.valueOf(user.toString()));
    }

}
