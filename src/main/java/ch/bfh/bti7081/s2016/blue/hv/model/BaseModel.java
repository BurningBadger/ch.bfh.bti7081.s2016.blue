package ch.bfh.bti7081.s2016.blue.hv.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import ch.bfh.bti7081.s2016.blue.hv.entities.BaseEntity;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;
import org.hibernate.metamodel.source.annotations.entity.EntityClass;

/**
 * {@link BaseModel} offering some CRUD operations.
 * 
 * @param <T>
 *            The entity type.
 * @param <ID>
 *            The Type of the primary-key field of {@link T}.
 */
public abstract class BaseModel<T extends BaseEntity, ID> implements Serializable {

    private static final long serialVersionUID = -7938358022103672493L;

    private final static Logger LOGGER = Logger.getLogger(BaseModel.class.getName());

    private JPAContainer<T> jpaContainer;

    private EntityManager entityManager;

    private Class<T> entityClass;

    public BaseModel(Class<T> clazz) {
	this.entityClass = clazz;
	jpaContainer = JPAContainerFactory.make(this.entityClass, Constants.PERSISTENCE_UNIT);
	entityManager = jpaContainer.getEntityProvider().getEntityManager();
    }

    /**
     * Save {@link T}
     * 
     * @param element
     * @return true, if saving was successful.
     */
    public boolean saveOrUpdate(T element) {
	try {
		Date timeStamp = new Date();
	    entityManager.getTransaction().begin();

	    // new, if no id exists
	    // TODO: remove check 999
//	    if (element.getId() == null || element.getId() == 999) {
	    if (element.getId() == null ) {
		element.setCreatedAt(timeStamp);
		element.setUpdatedAt(timeStamp);
		entityManager.persist(element);
	    }
	    else {
		element.setUpdatedAt(timeStamp);
		entityManager.merge(element);
	    }
	    entityManager.getTransaction().commit();
	}
	catch (RollbackException ex) {
	    LOGGER.log(Level.WARNING, ex.getMessage());
	    return false;
	}
	return true;
    }

    /**
     * Save a list of {@link T}
     * 
     * @param data
     * @return true, if saving was successful.
     */
    public boolean save(List<T> data) {
	try {
	    entityManager.getTransaction().begin();

	    for (T element : data) {
		entityManager.persist(element);
	    }
	    entityManager.getTransaction().commit();
	}
	catch (RollbackException ex) {
	    LOGGER.log(Level.WARNING, ex.getMessage());
	    return false;
	}

	return true;
    }

    /**
     * Finds {@link T} for the given ID.
     * 
     * @param id
     * @return the found {@link T}
     * @throws EntityNotFoundException
     *             if no {@link T} was found for the provided ID.
     */
    public T findById(ID id) throws EntityNotFoundException {

	entityManager.getTransaction().begin();

	T entity = entityManager.find(entityClass, id);

	entityManager.getTransaction().commit();

	if (entity == null) {
	    throw new EntityNotFoundException("Entity " + entityClass.getName() + " with id " + id + " not found");
	}
	return entity;
    }

    /**
     * Find all {@link T}
     * 
     * @return
     * @throws EntityNotFoundException
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws EntityNotFoundException {
	entityManager.getTransaction().begin();

	// get the table name from the defined annotation.
	Entity annotation = entityClass.getAnnotation(Entity.class);
	String tableName = annotation.name();

	List<T> list = entityManager.createQuery("SELECT t FROM " + tableName + " t").getResultList();
	entityManager.getTransaction().commit();
	return list;
    }

    /**
     * Delete {@link T}.
     * 
     * @param element
     * @return true, if successful
     */
    public boolean delete(T element) {
	try {
	    entityManager.getTransaction().begin();
	    entityManager.remove(element);
	    entityManager.getTransaction().commit();
	}
	catch (RollbackException ex) {
	    LOGGER.log(Level.WARNING, ex.getMessage());
	    return false;
	}
	return true;
    }

    /**
     * Delete {@link T} with the given ID.
     * 
     * @param id
     * @return true, if successful.
     */
    public boolean deleteById(ID id) {
	try {
	    T element = this.findById(id);
	    entityManager.getTransaction().begin();
	    entityManager.remove(element);
	    entityManager.getTransaction().commit();
	}
	catch (RollbackException ex) {
	    LOGGER.log(Level.WARNING, ex.getMessage());
	    return false;
	}
	return true;
    }

    public EntityTransaction getTransaction() {
	if (entityManager == null) {
	    return null;
	}
	return entityManager.getTransaction();
    }

    public T findHealthVisitor(HealthVisitor element) throws EntityNotFoundException{
	entityManager.getTransaction().begin();

	Entity annotation = entityClass.getAnnotation(Entity.class);
	String tableName = annotation.name();

	List<T> users = entityManager.createQuery(
	    "SELECT hv FROM " + tableName + " hv WHERE hv.userName LIKE :userName AND hv.password LIKE :userPass")
	    .setParameter("userName", element.getUserName())
	    .setParameter("userPass", element.getPassword())
	    .setMaxResults(1).getResultList();

	entityManager.getTransaction().commit();

	return users.size() > 0 ? users.get(0) : null;
    }
}
