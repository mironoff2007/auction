import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AuctionTest {

    String path=System.getProperty("user.dir")+"\\src\\test\\resources\\";

    @Test
    public void testNA() throws IOException {
        Auction auction = new Auction("NA.txt");
        auction.setWorkingDir(path);
        assertEquals("0 n/a", auction.getResult());
    }

    @Test
    public void test1() throws IOException {
        Auction auction = new Auction("test1.txt");
        auction.setWorkingDir(path);
        assertEquals("150 15.30", auction.getResult());
    }

    @Test
    public void test2() throws IOException {
        Auction auction = new Auction("test2.txt");
        auction.setWorkingDir(path);
        assertEquals("200 15.30", auction.getResult());
    }

    @Test
    public void test3() throws IOException {
        Auction auction = new Auction("test3.txt");
        auction.setWorkingDir(path);
        assertEquals("600 2.00", auction.getResult());
    }

    @Test
    public void test4() throws IOException {
        Auction auction = new Auction("test4.txt");
        auction.setWorkingDir(path);
        assertEquals("150 85.00", auction.getResult());
    }
}