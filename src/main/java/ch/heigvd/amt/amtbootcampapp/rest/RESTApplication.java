
package ch.heigvd.amt.amtbootcampapp.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * An API REST application.
 *
 * @author F. Franchini, S. Henneberger
 */
@ApplicationPath("/api")
public class RESTApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        // Disable Moxy and use Jackson for Glassfish, and doesnt't bug with wildfly
        properties.put("jersey.config.disableMoxyJson", true);

        return properties;
    }
}
