package com.company;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

import static java.lang.StrictMath.*;

public class ElGamal {


    ElGamal() {

    }


    public int getMod(double num1, int power, double mod) {
        int res = 1;
        ArrayList<Integer> moveList = new ArrayList<Integer>();
        int tmp = power;
        while (tmp > 0) {
            if (tmp % 2 == 1) {
                moveList.add(1);
                tmp -= 1;
            } else {
                moveList.add(2);
                tmp >>= 1;
            }
        }
        Collections.reverse(moveList);
        for (int i = 0; i < moveList.size(); i++) {
            if (moveList.get(i) == 1) {
                res *= num1;
            } else {
                res *= res;
            }
            res %= mod;
        }
        return res;
    }

    public boolean checkPrime(double num, double a) {
        int s = 0;
        int d = (int) (num - 1);
        while (d % 2 == 0 && d != 0) {
            d >>= 1;
            s += 1;
        }
        if (getMod(a, d, num) == 1)
            return true;
        for (int i = 0; i < s + 1; i++) {
            if (getMod(a,d *(2^i), num)== num - 1){
                return true;
            }
        }
        return false;
    }


    public double getPrime(int power) {
        int max = (int) pow(2,power);
        int min = (int) (pow(2,(power - 1)) + 1);
        Random rnd = new Random();
        while (true) {
            BigInteger p = new BigInteger(power,  ((rnd.nextInt(max) + min));
            int count = 0;
            int steps = 1000;
            for (int i = 0; i < steps; i++) {
                double a =((Math.random() * (pow(2, (power - 2)) + 1) + 10));
                if (checkPrime(p, a))
                    count += 1;
                else break;
            }
            if (count == steps) {
                return p;
            }
        }
    }



    public double getG(double p) {
        while (true) {
            int g = (int) (Math.random() * p);
            ArrayList<Integer> oldG = new ArrayList<Integer>();
            int steps = 10;
            for (int i = 0; i < steps; i++) {
                int tmp = getMod(g, i, p);
                if (oldG.contains(tmp) == true) {
                    break;
                }
                oldG.add(tmp);
            }
            if (oldG.size() == steps) {
                return g;
            }
        }
    }


    public  void getKeys(int power, int subs, int[] password) {
        if(password.length > 0) {
            Random rand = new Random(password.hashCode());
        }
        return;
    }
}





