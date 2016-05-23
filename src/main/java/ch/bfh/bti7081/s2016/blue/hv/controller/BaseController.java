package ch.bfh.bti7081.s2016.blue.hv.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import ch.bfh.bti7081.s2016.blue.hv.util.Constants;

public class BaseController<T, ID> implements Serializable {

    private static final long serialVersionUID = -7938358022103672493L;

    private final static Logger LOGGER = Logger.getLogger(BaseController.class.getName());

    private JPAContainer<T> entities;

    private EntityManager entityManager;

    private Class<T> entityClass;

    public BaseController(Class<T> clazz) {
	this.entityClass = clazz;
	entities = JPAContainerFactory.make(this.entityClass, Constants.PERSISTENCE_UNIT);
	entityManager = entities.getEntityProvider().getEntityManager();
    }

    /**
     * Save {@link T}
     * 
     * @param element
     * @return true, if saving was successful.
     */
    public boolean save(T element) {
	try {
	    entityManager.getTransaction().begin();
	    entityManager.merge(element);
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
	List<T> list = entityManager.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t").getResultList();
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

}
