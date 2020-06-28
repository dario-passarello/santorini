package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * It's a Build Behavior decorator that adds the possibility to choose between a normal build and a dome
 */
public class AnyDomeBuild extends BuildDecorator {

    /**
     * The constructor method. It decorates the parameter with this class
     * @param buildBehavior The Build Behavior target
     */
    public AnyDomeBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
    }


    /**
     * It is like the StandardBuild, but adds the possibility to choose between a normal build and a dome
     * @param dest the square where the builder want to build
     * @return false if the build phase ended, else true
     */
    public boolean build(Square dest) {
        return wrappedBuildBehavior.build(dest);
    }

    public Set<Square> neighborhood(Square src) {
        return wrappedBuildBehavior.neighborhood(src);
    }

    public BuildBehavior copyBehavior(){
        return new AnyDomeBuild(wrappedBuildBehavior.copyBehavior());
    }


}
