import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TailTest {
private void AssertFileContent ( String out, String expectedOut) throws FileNotFoundException {
    Scanner outfile = new Scanner(new File ( out));
    Scanner expfile = new Scanner(new File(expectedOut));
    assertEquals(new File("src/files/" + out).length(), new File("src/files/" + expectedOut).length());
    while (outfile.hasNext()) {
        assertEquals(outfile.nextLine(), expfile.nextLine());
    }
}
    @Test
    public void main() throws IOException {
    String [] args = {"-n", "2", "-o", "src/files/out1.txt", "src/files/text1.txt" , "src/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/files/expected1.txt" , "src/files/out1.txt");
    }
    @Test
    public void main4() throws IOException {
        String [] args = {"-c", "2", "-o", "src/files/out2.txt", "src/files/text1.txt" , "src/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/files/expected2.txt" , "src/files/out2.txt");
    }
    @Test
    public void main3() throws IOException {
        String [] args = {"-n", "2", "src/files/text1.txt" , "src/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/files/expected1.txt" , "src/files/out1.txt");
    }
    @Test
    public void main2() throws IOException {
        String [] args = {"-n", "1", "src/files/text1.txt" };
        Tail.main(args);
        AssertFileContent( "src/files/expected3.txt" , "src/files/out1.txt");
    }
    @Test
    public void main1() throws IOException {
        String [] args = {"-c", "5", "-o", "src/files/out1.txt", "src/files/text2.txt" };
        Tail.main(args);
        AssertFileContent( "src/files/expected3.txt" , "src/files/out1.txt");
    }
    @Test
   public void main14() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        String [] args={"-c", "5", "src/files/text1.txt"};
        Tail.main(args);
        System.out.flush();
        System.setOut(old);
        System.out.println("+"+baos.toString());
    }
}
