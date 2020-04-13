package utils;

import model.Square;
import java.util.Set;

public interface Observer {
    /**
     * General update
     */
    void update();


    /**
     * Update method with a boolean as input
     * @param power
     */
    void update(boolean power);

    /**
     * Update method with a Set of Squares and a boolean as inputs
     * @param set
     * @param power
     */
    void update(Set<Square> set, boolean power);
}
