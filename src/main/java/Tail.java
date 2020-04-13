import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {
    @Argument (usage = "files")
    private List<String> files ;
    @Option(name = "-c", forbids = "-n")
    private int countSymbols;
    @Option(name = "-n", forbids = "-c")
    private int countLines;
    @Option(name = "-o")
    private File outFile;
    public static void main(String[] args) throws IOException {
        new Tail().launch(args);
    }
    private void launch (String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Tail.jar [-c num|-n num] [-o ofile] file0 file1 file2...");
            parser.printUsage(System.out);
        }
        StringBuilder res = new StringBuilder();
        if (files.isEmpty()) {
            Scanner inn = new Scanner(System.in);
            String in = inn.next();
            System.out.println("Enter input file name, please");
            writer(outFile, extract(in));
        }
        if (files.size() == 1) {
            res.append(extract(files.get(0)));
        }else for (String file : files) {
            File f =  new File(file);
            res.append(f.getName());
            res.append("\n");
            res.append(extract(file));
        }
        if (outFile == null) {
            System.out.println(res.toString());
        }else writer(outFile, res.toString());
        }
    private String extract (String file) throws IOException {
        String result = "" ;
        if (countLines == 0 && countSymbols == 0) {
            countLines = 10;
        }
        if (countLines!= 0 && countSymbols!= 0 ) {
            System.err.println("Ошибка");
        }
        if (countLines != 0 ) {
            result = extrl(file);
        }
        if (countSymbols != 0) {
            result = extractSymbols(file);
        }
        return result;
    }
    private String extrl (String file) throws FileNotFoundException {
        Scanner scan = new Scanner(readFile(file));
        StringBuilder res = new StringBuilder();
        List<String> l = new ArrayList<>();
        while (scan.hasNext()) {
            l.add(scan.nextLine());
        }
        for (int i = l.size() - countLines; i < l.size(); i++) {
            res.append(l.get(i));
            res.append("\n");
        }
        return res.toString();
    }
    private String extractSymbols(String file) throws IOException {
        Scanner scan = new Scanner(readFile(file));
        StringBuilder res = new StringBuilder();
        while (scan.hasNext()) {
            res.append(scan.next());
        }
        res.delete(0 , res.length() - countSymbols);
        res.append("\n");
        return res.toString();
    }
    private BufferedReader readFile (String file) throws FileNotFoundException {
        BufferedReader input = new BufferedReader(new FileReader(file));
        return input;
    }
    private void writer(File file, String text) throws IOException {
        FileWriter writer = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(writer);
        br.write(text);
        br.write("\n");
        br.flush();
        br.close();
    }
}


