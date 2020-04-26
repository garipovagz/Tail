import com.g.Tail;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TailTest {
private void AssertFileContent ( String out, String expectedOut) throws FileNotFoundException {
    Scanner outfile = new Scanner(new File (out));
    Scanner expected = new Scanner(new File(expectedOut));
    assertEquals(new File("src/files/" + out).length(), new File("src/files/" + expectedOut).length());
    while (outfile.hasNext()) {
        assertEquals(outfile.nextLine(), expected.nextLine());
    }
}
    @Test
    public void test1() throws IOException {
    String [] args = {"-n", "2", "-o", "src/main/resources/files/out1.txt", "src/main/resources/files/text1.txt" , "src/main/resources/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/main/resources/files/expected1.txt" , "src/main/resources/files/out1.txt");
    }
    @Test
    public void test2() throws IOException {
        String [] args = {"-c", "2", "-o", "src/main/resources/files/out2.txt", "src/main/resources/files/text1.txt" , "src/main/resources/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/main/resources/files/expected2.txt" , "src/main/resources/files/out2.txt");
    }
    @Test
    public void test3() throws IOException {
        String [] args = {"-c", "5", "-o", "src/main/resources/files/out1.txt", "src/main/resources/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/main/resources/files/expected3.txt" , "src/main/resources/files/out1.txt");
    }
    @Test
   public void test4() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        String [] args={"-c", "5", "src/main/resources/files/text1.txt"};
        Tail.main(args);
        System.out.flush();
        System.setOut(old);
        System.out.println("+"+baos.toString());
    }
    @Test
    public void test5() throws IOException {
        String [] args = {"-c", "5", "-o", "src/main/resources/files/out1.txt" };
        Tail.main(args);
        AssertFileContent( "src/main/resources/files/expected4.txt" , "src/main/resources/files/out1.txt");
    }
    @Test
    public void test6() throws IOException {
        String [] args = {"-c", "0", "-o", "src/main/resources/files/out1.txt" };
        Tail.main(args);
        AssertFileContent( "src/main/resources/files/expected4.txt" , "src/main/resources/files/out1.txt");
    }
}
