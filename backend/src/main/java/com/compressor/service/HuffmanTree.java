package com.compressor.service;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private HuffmanNode root;

    public HuffmanTree(int[] freq) {
        this.root = buildTree(freq);
    }

    public HuffmanNode getRoot() {
        return root;
    }

    /* ================= BUILD TREE ================= */

    private HuffmanNode buildTree(int[] freq) {

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

        // Create leaf nodes
        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                pq.add(new HuffmanNode((byte) i, freq[i]));
            }
        }

        // Edge case: single character file
        if (pq.size() == 1) {
            pq.add(new HuffmanNode((byte) 0, 1));
        }

        // Build tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            HuffmanNode parent =
                    new HuffmanNode(left.frequency + right.frequency, left, right);

            pq.add(parent);
        }

        return pq.poll();
    }

    /* ================= BUILD CODES ================= */

    public Map<Byte, String> buildCodes() {
        Map<Byte, String> codeMap = new HashMap<>();
        buildCodeRecursive(root, "", codeMap);
        return codeMap;
    }

    private void buildCodeRecursive(HuffmanNode node,
                                    String code,
                                    Map<Byte, String> map) {

        if (node == null)
            return;

        if (node.isLeaf()) {
            map.put(node.data, code.length() > 0 ? code : "0");
            return;
        }

        buildCodeRecursive(node.left, code + "0", map);
        buildCodeRecursive(node.right, code + "1", map);
    }
}
