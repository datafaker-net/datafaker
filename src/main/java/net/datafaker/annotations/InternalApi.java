package net.datafaker.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;

/**
 * Any element annotated with this annotation, is meant for internal use ONLY.
 * Usually these methods are made non-private only for calling from tests.
 * <p>
 * Users of DataFaker should not call methods annotated with {@link InternalApi}.
 * </p>
 * Otherwise, your code can break with any new release.
 */
@Inherited
@Documented
public @interface InternalApi {
}
