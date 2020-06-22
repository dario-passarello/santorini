package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnyDomeBuild extends BuildDecorator {

    public AnyDomeBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
    }

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
