package com.company;

public enum Parameters {

    SHA3_224 (1152, 0x06, 224),
    SHA3_256 (1088, 0x06, 256),
    SHA3_384 (832, 0x06, 384),
    SHA3_512 (576, 0x06, 512);

    private final int rate;


    public final int d;

    public final int outputLen;

     Parameters(int rate, int d, int outputLen) {
        this.rate = rate;
        this.d = d;
        this.outputLen = outputLen;
    }

    public int getRate() {
        return rate;
    }

    public int getD() {
        return d;
    }

    public int getOutputLen() {
        return outputLen;
    }
}