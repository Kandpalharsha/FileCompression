package com.compressor.service;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    private OutputStream out;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeBit(int bit) throws IOException {
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;

        if (numBitsFilled == 8) {
            out.write(currentByte);
            numBitsFilled = 0;
            currentByte = 0;
        }
    }

    public int closeAndGetPadding() throws IOException {
        int padding = 0;
        if (numBitsFilled > 0) {
            padding = 8 - numBitsFilled;
            currentByte <<= padding;
            out.write(currentByte);
        }
        out.flush();
        return padding;
    }
}
