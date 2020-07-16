package com.company;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import static com.company.Parameters.*;
import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;


public class Keccak {
    private static BigInteger BIT_64 = new BigInteger("18446744073709551615");
    int[] uState = new int[200];
    int[] uMessage;
    int rateInBytes = 0;
    int blockSize = 0;
    int inputOffset = 0;
    Parameters parameter;
    public Keccak(String mod) {
        initMod(mod);
    }

    public byte[] getHash(final byte[] message) {
        uMessage = HexUtils.convertToUint(message);
        rateInBytes = parameter.getRate() / 8;
        Absorbing();
        Padfunc();

        return Squeezing();
    }

    public void Absorbing()
    {
        while (inputOffset < uMessage.length) {
            blockSize = Math.min(uMessage.length - inputOffset, rateInBytes);
            for (int i = 0; i < blockSize; i++) {
                uState[i] = uState[i] ^ uMessage[i + inputOffset];
            }

            inputOffset = inputOffset + blockSize;
            if (blockSize == rateInBytes) {
                ffunc(uState);
                blockSize = 0;
            }
        }
    }

    public void Padfunc()
    {
        uState[blockSize] = uState[blockSize] ^ this.parameter.getD();
        if ((this.parameter.getD() & 0x80) != 0 && blockSize == (rateInBytes - 1)) {
            ffunc(uState);
        }
        uState[rateInBytes - 1] = uState[rateInBytes - 1] ^ 0x80;
        ffunc(uState);

    }

    public byte[] Squeezing()
    {
        ByteArrayOutputStream byteResults = new ByteArrayOutputStream();
        int tOutputLen = parameter.getOutputLen() / 8;
        while (tOutputLen > 0) {
            blockSize = Math.min(tOutputLen, rateInBytes);
            for (int i = 0; i < blockSize; i++) {
                byteResults.write((byte) uState[i]);
            }

            tOutputLen -= blockSize;
            if (tOutputLen > 0) {
                ffunc(uState);
            }
        }
       return byteResults.toByteArray();
    }

    private void ffunc(final int[] uState) {
        BigInteger[][] lState = new BigInteger[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int[] data = new int[8];
                arraycopy(uState, 8 * (i + 5 * j), data, 0, data.length);
                lState[i][j] = HexUtils.convertFromLittleEndianTo64(data);
            }
        }
        round(lState);

        fill(uState, 0);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int[] data = HexUtils.convertFrom64ToLittleEndian(lState[i][j]);
                arraycopy(data, 0, uState, 8 * (i + 5 * j), data.length);
            }
        }

    }



    private void round(final BigInteger[][] state) {
        int LFSRstate = 1;
        for (int round = 0; round < 24; round++) {
            BigInteger[] C = new BigInteger[5];
            BigInteger[] D = new BigInteger[5];

            // θ
            for (int i = 0; i < 5; i++) {
                C[i] = state[i][0].xor(state[i][1]).xor(state[i][2]).xor(state[i][3]).xor(state[i][4]);
            }

            for (int i = 0; i < 5; i++) {
                D[i] = C[(i + 4) % 5].xor(HexUtils.leftRotate64(C[(i + 1) % 5], 1));
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    state[i][j] = state[i][j].xor(D[i]);
                }
            }

            //ρ  π
            int x = 1, y = 0;
            BigInteger current = state[x][y];
            for (int i = 0; i < 24; i++) {
                int tX = x;
                x = y;
                y = (2 * tX + 3 * y) % 5;

                BigInteger shiftValue = current;
                current = state[x][y];

                state[x][y] = HexUtils.leftRotate64(shiftValue, (i + 1) * (i + 2) / 2);
            }

            //χ
            for (int j = 0; j < 5; j++) {
                BigInteger[] t = new BigInteger[5];
                for (int i = 0; i < 5; i++) {
                    t[i] = state[i][j];
                }

                for (int i = 0; i < 5; i++) {
                    BigInteger invertVal = t[(i + 1) % 5].xor(BIT_64);
                    state[i][j] = t[i].xor(invertVal.and(t[(i + 2) % 5]));
                }
            }

            for (int i = 0; i < 7; i++) {
                LFSRstate = ((LFSRstate << 1) ^ ((LFSRstate >> 7) * 0x71)) % 256;

                int bitPosition = (1 << i) - 1;
                if ((LFSRstate & 2) != 0) {
                    state[0][0] = state[0][0].xor(new BigInteger("1").shiftLeft(bitPosition));
                }
            }
        }
    }


    private void initMod(String mod) {

        if(mod == "224") {
            parameter = SHA3_224;
        }
        else  if(mod == "256")
        {
            parameter = SHA3_256;
        }
        else  if(mod == "384")
        {
            parameter = SHA3_384;
        }
        else  if(mod == "512")
        {
            parameter = SHA3_512;
        }
        else
        {
            System.out.println("Incorrect enter");
            System.exit(0);
        }
    }

}
