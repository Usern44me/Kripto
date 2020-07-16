package com.company;
import com.company.AES;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        AES t;
            t = new AES();
        t.run("in.txt", "out.txt");
       // t.run("crypt.bmp", "out.bmp", false);
    }
}