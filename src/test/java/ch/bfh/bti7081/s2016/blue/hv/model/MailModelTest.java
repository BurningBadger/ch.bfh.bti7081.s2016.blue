package ch.bfh.bti7081.s2016.blue.hv.model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for {@link MailModel}.
 */
public class MailModelTest {

    /**
     * Start the fakeSMTP agent.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @BeforeClass // only initialize once
    public static void setUp() throws IOException, InterruptedException {

	File file = new File("");
	String baseDir = file.getAbsolutePath();

	String jar = baseDir + "/fakeSMTP-2.0.jar";
	String outputDir = baseDir + "/target";

	// cleanup previously generated files
	File targetDir = new File(outputDir);
	File[] emails = targetDir.listFiles((FilenameFilter) new SuffixFileFilter("eml"));
	for (File email : emails) {
	    email.delete();
	}

	// start the fakeSMTP agent
	Runtime.getRuntime().exec("java -jar " + jar + " -s -p 2525 -o " + outputDir);
	Thread.sleep(5000); // wait until started up
    }

    @Test
    public void testSendEmail() throws IOException {

	// given
	String from = "supervisor@healthvis.bfh.com";
	String subject = "This is the subject.";
	String text = "Text: This is the text for this email.";
	String recipients = "receiver@healthvis.bfh.com";

	// when
	MailModel.send(from, subject, text, recipients);

	// then
	File file = new File("");
	String baseDir = file.getAbsolutePath();
	File targetDir = new File(baseDir + "/target");
	File[] emails = targetDir.listFiles((FilenameFilter) new SuffixFileFilter("eml"));
	for (File email : emails) {
	    @SuppressWarnings("unchecked")
	    List<String> lines = (List<String>) FileUtils.readLines(email);

	    Assert.assertEquals("This is the subject.", find("Subject", lines));
	    Assert.assertEquals("This is the text for this email.", find("Text", lines));
	    Assert.assertEquals("supervisor@healthvis.bfh.com", find("From", lines));
	    Assert.assertEquals("receiver@healthvis.bfh.com", find("To", lines));
	}
    }

    /**
     * Extract the lines from the email file.
     */
    private Object find(String key, List<String> lines) {
	for (String line : lines) {
	    String[] keyValue = line.split(":");
	    if (keyValue[0].equals(key))
		return keyValue[1].trim();
	}
	return null;
    }
}
