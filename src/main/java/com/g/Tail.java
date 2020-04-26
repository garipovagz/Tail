package com.g;

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
    @Option(name = "-c", forbids = "-n", usage = "countS")
    private int countSymbols;
    @Option(name = "-n", forbids = "-c", usage = "countL")
    private int countLines;
    @Option(name = "-o", usage = "outFile")
    private File outFile;

    public Tail() {
    }

    public static void main(String[] args)  {
        new Tail().launch(args);
    }
    private void launch (String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar com.g.Tail.jar [-c num|-n num] [-o ofile] file0 file1 file2...");
            parser.printUsage(System.out);
            return;
        }
        try {
            StringBuilder res = new StringBuilder();
            for (String f : files) {
                if (f == null) {
                    System.out.println("Enter your text, please");
                    Scanner inn = new Scanner(System.in);
                    res.append(extract(inn));
                }
            }
            if (files.isEmpty()) {
                System.out.println("Enter your text, please");
                Scanner inn = new Scanner(System.in);
                res.append(extract(inn));
            }
            if (files.size() == 1) {
                res.append(extract(readFile(files.get(0))));
            } else for (String file : files) {
                File f = new File(file);
                res.append(f.getName());
                res.append(System.lineSeparator());
                res.append(extract(readFile(file)));
            }
            if (outFile == null) {
                System.out.println(res.toString());
            } else writer(outFile, res.toString());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    private String extract (Scanner file )  {
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
    private String extrl (Scanner scan)  {
        StringBuilder res = new StringBuilder();
        List<String> l = new ArrayList<>();
        while (scan.hasNext()) {
            l.add(scan.nextLine());
        }
        if (countLines < l.size()) {
            for (int i = l.size() - countLines; i < l.size(); i++) {
                res.append(l.get(i));
                res.append(System.lineSeparator());
            }
        } else {
            System.out.println("В тексте нет столько строк");
        }
        return res.toString();
    }
    private String extractSymbols(Scanner scan )  {
        StringBuilder res = new StringBuilder();
        while (scan.hasNext()) {
            res.append(scan.next());
        }
        if (res.length() > countSymbols) {
            res.delete(0, res.length() - countSymbols);
            res.append(System.lineSeparator());
        }else {
            System.err.println("В тексте нет столько символов");
        }
        return res.toString();
    }
    private static Scanner readFile(String file) throws FileNotFoundException {
       BufferedReader input = new BufferedReader(new FileReader(file)) ;
           return new Scanner(input);
       }

    private void writer(File file, String text) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(text);
            br.write(System.lineSeparator());
        }
    }
}