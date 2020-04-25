package utils;


import org.junit.Test;
import org.junit.Assert;

public class CoordinateTest{

    @Test
    public void test(){
        Coordinate c = new Coordinate(2,3);
        Coordinate d = new Coordinate(2,3);
        Coordinate e = new Coordinate(4,5);

        Assert.assertEquals(c, d);
        Assert.assertNotEquals(d, e);
        Assert.assertEquals("(4,5)", e.toString());
        Assert.assertEquals(Integer.valueOf(4), e.getX());
        Assert.assertEquals(Integer.valueOf(5), e.getY());
    }
}
