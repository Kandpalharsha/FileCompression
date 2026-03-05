package com.compressor.service;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class HuffmanService {

    /* ================== COMPRESS ================== */

    public byte[] compress(byte[] inputBytes) throws IOException {

        int[] freq = new int[256];
        for (byte b : inputBytes) {
            freq[b & 0xFF]++;
        }

        HuffmanTree tree = new HuffmanTree(freq);
        Map<Byte, String> codeMap = tree.buildCodes();

        // Step 1: Compress bits into buffer
        ByteArrayOutputStream bitBuffer = new ByteArrayOutputStream();
        BitOutputStream bitOut = new BitOutputStream(bitBuffer);

        for (byte b : inputBytes) {
            String code = codeMap.get(b);
            for (char c : code.toCharArray()) {
                bitOut.writeBit(c == '1' ? 1 : 0);
            }
        }

        int padding = bitOut.closeAndGetPadding();
        byte[] compressedBits = bitBuffer.toByteArray();

        // Step 2: Write final structure
        ByteArrayOutputStream finalOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(finalOut);

        for (int i = 0; i < 256; i++) {
            dataOut.writeInt(freq[i]);
        }
        System.out.println("First 10 freq values during compression:");
        for (int i = 0; i < 10; i++) {
            System.out.print(freq[i] + " ");
        }
        System.out.println();


        dataOut.writeInt(padding);
        dataOut.write(compressedBits);

        dataOut.flush();
        dataOut.close();

        byte[] finalBytes = finalOut.toByteArray();

        System.out.println("Compressed file first 16 bytes:");
        for (int i = 0; i < 16; i++) {
            System.out.print((finalBytes[i] & 0xFF) + " ");
        }
        System.out.println();

        return finalBytes;

    }



    /* ================== DECOMPRESS ================== */

    public byte[] decompress(byte[] compressedBytes) throws IOException {
        System.out.println("Compressed file size received: " + compressedBytes.length);
        System.out.println("First 16 bytes inside decompress:");
        for (int i = 0; i < 16; i++) {
            System.out.print((compressedBytes[i] & 0xFF) + " ");
        }
        System.out.println();

        int index = 0;

        // 1️⃣ Read frequency table directly from byte array
        int[] freq = new int[256];
        for (int i = 0; i < 256; i++) {
            freq[i] = ((compressedBytes[index] & 0xFF) << 24) |
                    ((compressedBytes[index + 1] & 0xFF) << 16) |
                    ((compressedBytes[index + 2] & 0xFF) << 8) |
                    (compressedBytes[index + 3] & 0xFF);
            index += 4;
        }

        // 2️⃣ Read padding
        int padding = ((compressedBytes[index] & 0xFF) << 24) |
                ((compressedBytes[index + 1] & 0xFF) << 16) |
                ((compressedBytes[index + 2] & 0xFF) << 8) |
                (compressedBytes[index + 3] & 0xFF);
        index += 4;

        // 3️⃣ Build tree
        HuffmanTree tree = new HuffmanTree(freq);
        HuffmanNode root = tree.getRoot();

        int totalChars = 0;
        for (int f : freq)
            totalChars += f;

        System.out.println("Total chars from freq: " + totalChars);
        System.out.println("First few freq values: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(freq[i] + " ");
        }
        System.out.println();

        // Edge case
        if (root != null && root.isLeaf()) {
            ByteArrayOutputStream singleCharOut = new ByteArrayOutputStream();
            for (int i = 0; i < totalChars; i++)
                singleCharOut.write(root.data);
            return singleCharOut.toByteArray();
        }

        // 4️⃣ Remaining bytes are compressed bit data
        byte[] bitData = Arrays.copyOfRange(compressedBytes, index, compressedBytes.length);

        BitInputStream bitIn =
                new BitInputStream(new ByteArrayInputStream(bitData));

        int totalBits = (bitData.length * 8) - padding;
        int bitsRead = 0;

        ByteArrayOutputStream decodedOut = new ByteArrayOutputStream();
        HuffmanNode current = root;
        int decodedCount = 0;

        while (decodedCount < totalChars && bitsRead < totalBits) {

            int bit = bitIn.readBit();
            if (bit == -1) break;

            bitsRead++;

            current = (bit == 0) ? current.left : current.right;

            if (current.isLeaf()) {
                decodedOut.write(current.data);
                current = root;
                decodedCount++;
            }
        }

        return decodedOut.toByteArray();
    }




    /* ================== TREE BUILDING ================== */

    private HuffmanNode buildTree(int[] freq) {

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

        for (int i = 0; i < 256; i++)
            if (freq[i] > 0)
                pq.add(new HuffmanNode((byte) i, freq[i]));

        if (pq.size() == 1)
            pq.add(new HuffmanNode((byte) 0, 1));

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.add(new HuffmanNode(left.frequency + right.frequency, left, right));
        }

        return pq.poll();
    }

    private void buildCodes(HuffmanNode node, String code, Map<Byte, String> map) {
        if (node == null) return;

        if (node.isLeaf()) {
            map.put(node.data, code.length() > 0 ? code : "0");
            return;
        }

        buildCodes(node.left, code + "0", map);
        buildCodes(node.right, code + "1", map);
    }
}
