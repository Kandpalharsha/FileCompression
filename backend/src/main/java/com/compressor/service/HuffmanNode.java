package com.compressor.service;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public byte data;
    public int frequency;
    public HuffmanNode left, right;

    public HuffmanNode(byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.frequency - o.frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
