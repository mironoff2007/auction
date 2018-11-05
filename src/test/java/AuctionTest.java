import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AuctionTest {

    String path=System.getProperty("user.dir")+"\\src\\test\\resources\\";

    @Test
    public void testNA() {
        Auction auction = new Auction("NA.txt");
        System.out.println(path);
        assertEquals("0 n/a", auction.getResult());
    }
}