package view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This Tag defines whatever is considered a listener
 */
@Target(ElementType.METHOD)
public @interface ServerListener {
}
