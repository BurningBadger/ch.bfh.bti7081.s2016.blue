package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Setting;
import ch.bfh.bti7081.s2016.blue.hv.enums.SettingKeys;

/**
 * The model for managing the settings.
 * 
 * @author nicolasschmid
 */
public class SettingsModel extends BaseModel<Setting, Long> {

    private static final long serialVersionUID = -8064364994519485452L;

    private final static Logger LOGGER = Logger.getLogger(SettingsModel.class.getName());

    private HealthVisitorsModel healthVisitorsModel;

    public SettingsModel() {
	super(Setting.class);
	healthVisitorsModel = new HealthVisitorsModel();
    }

    /**
     * Find the locale for the current logged in user.
     * 
     * @return The user's locale or Locale.GERMAN if none is set.
     */
    public Locale findLocaleForLoggedInUser() {
	HealthVisitor hv = healthVisitorsModel.findCurrentHealthVisitor();
	Set<Setting> settings = hv.getSettings();
	Optional<Setting> s = settings.stream().filter(p -> {
	    return p.getKey().equals(SettingKeys.LOCALE.name());
	}).findFirst();

	if (s.isPresent()) {
	    return new Locale(s.get().getValue());
	}
	return Locale.GERMAN;
    }

}
