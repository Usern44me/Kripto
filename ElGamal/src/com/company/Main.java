package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ElGamal el = new ElGamal();
        System.out.println(el.getPrime(128));
    }
}
