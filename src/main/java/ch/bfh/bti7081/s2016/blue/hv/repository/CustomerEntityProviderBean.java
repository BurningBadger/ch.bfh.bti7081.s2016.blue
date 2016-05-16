package ch.bfh.bti7081.s2016.blue.hv.repository;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

import ch.bfh.bti7081.s2016.blue.hv.model.Customer;

//@Stateless
//@TransactionManagement
public class CustomerEntityProviderBean extends MutableLocalEntityProvider<Customer> {

	public CustomerEntityProviderBean() {
		super(Customer.class);
		init();
		setTransactionsHandledByProvider(false);
	}

	// @PersistenceContext
	// private EntityManager em;

	@Override
	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected void runInTransaction(Runnable operation) {
		super.runInTransaction(operation);
	}

	// @PostConstruct
	public void init() {
		EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit("healthVisApp");
		setEntityManager(em);
		/*
		 * The entity manager is transaction-scoped, which means that the
		 * entities will be automatically detached when the transaction is
		 * closed. Therefore, we do not need to explicitly detach them.
		 */
		setEntitiesDetached(false);
	}
}