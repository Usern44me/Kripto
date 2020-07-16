package com.company;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.company.HexUtils.convertBytesToString;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get("a.txt"));
        Keccak keccak = new Keccak("512");
     //   System.out.println(convertBytesToString(keccak.getHash(data)));
        FileWriter nFile = new FileWriter("file1.txt");
        nFile.write(convertBytesToString(keccak.getHash(data)));
        nFile.close();
    }
}

