
package ch.heigvd.amt.amtbootcampapp.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.HttpMethod;

/**
 * Add JAX-RS PATCH annotation.
 * By default, we can use PUT annotation for PUT HTTP request, but not PATCH.
 *
 * @see
 * <a href="http://stackoverflow.com/questions/17897171/how-to-have-a-patch-annotation-for-jax-rs">Source
 * from stackoverflow</a>
 * @author F. Franchini, S. Henneberger
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH {
}
