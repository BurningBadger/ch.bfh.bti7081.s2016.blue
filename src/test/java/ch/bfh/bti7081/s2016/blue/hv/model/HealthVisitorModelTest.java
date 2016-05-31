package ch.bfh.bti7081.s2016.blue.hv.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;

import java.util.List;

public class HealthVisitorModelTest {

    private HealthVisitorsModel healthVisitor = new HealthVisitorsModel();

    @Ignore
    @Test
    public void getAllVisitors(){
	List<HealthVisitor> visitors = healthVisitor.findAll();
    }

    @Ignore
    @Test
    public void getVisitorById(){
	HealthVisitor visitor = healthVisitor.findById(Long.valueOf(50));
    }

    @Ignore
    @Test
    public void loginHealthVisitor() {
	// given
	String login = "user1@test.com";
	String pass = "user1test";
	HealthVisitor hv = new HealthVisitor();

	hv.setUserName(login);
	hv.setPassword(pass);

	//when
	HealthVisitor visitor = healthVisitor.findHealthVisitor(hv);

	// then
	// firstname and lastname has been generated randomize
	//Assert.assertEquals("Marge", healthVisitor.findAll().get(0).getFirstname());
	//Assert.assertEquals("Smith", healthVisitor.findAll().get(0).getLastname());
    }
}
