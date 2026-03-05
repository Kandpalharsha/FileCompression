# Huffman File Compressor Web Application

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-Backend-green)
![React](https://img.shields.io/badge/React-Frontend-blue)
![Algorithm](https://img.shields.io/badge/Algorithm-HuffmanCoding-purple)
![Deployment](https://img.shields.io/badge/Deployment-Vercel%20%7C%20Render-black)

# Huffman File Compressor Web Application

A full-stack web application that allows users to **compress and decompress text files using the Huffman Coding algorithm**. 
The project demonstrates how lossless compression works by implementing Huffman encoding at the bit level and exposing it through a web interface.

---

## Features

- Compress text files using **Huffman Coding**
- Decompress `.huff` files back to the original file
- Bit-level file encoding and decoding
- Web-based interface for uploading and downloading files
- REST API built with Spring Boot
- Modern React frontend

---

## Tech Stack

### Frontend
- React (Vite)
- CSS
- JavaScript

### Backend
- Java
- Spring Boot
- REST API

### Algorithm
- Huffman Coding (Greedy Algorithm)
- Priority Queue
- Binary Tree

### Deployment
- Frontend: Vercel
- Backend: Render

### Project Architecture
User
│
▼
React Frontend (Vercel)
│
▼
Spring Boot API (Render)
│
▼
Java Huffman Compression Engine


---

## How Compression Works

1. The system reads the input file and calculates **character frequencies**.
2. A **Huffman Tree** is built using a priority queue.
3. Each character receives a **variable-length binary code**.
4. The encoded bits are written into a compressed file along with metadata.
5. During decompression, the Huffman Tree is reconstructed using the stored frequency table.

---

## Project Structure

FileCompression
│
├── backend
│ ├── src/main/java/com/compressor
│ │ ├── controller
│ │ ├── service
│ │ ├── HuffmanNode
│ │ ├── HuffmanTree
│ │ ├── BitInputStream
│ │ ├── BitOutputStream
│ │ └── CompressorApplication
│ └── pom.xml
│
├── frontend
│ ├── src
│ │ ├── App.jsx
│ │ ├── App.css
│ │ └── main.jsx
│ └── package.json
│
└── README.md

---

## API Endpoints

### Compress File
POST /api/compress
Upload a text file and receive a compressed `.huff` file.

### Decompress File
POST /api/decompress
Upload a `.huff` file and receive the original file.

---
## Example Workflow

1. Upload a `.txt` file
2. Click **Compress**
3. Download the `.huff` file
4. Upload the `.huff` file
5. Click **Decompress**
6. Download the original file

---
## Live Demo

Frontend:
https://file-compression-amber.vercel.app/

Backend API:
https://filecompression-561i.onrender.com
## Learning Outcomes

This project demonstrates:

- Implementation of **Huffman Coding**
- Bit-level file I/O operations
- Binary tree construction
- REST API development using Spring Boot
- Full-stack integration with React
- File upload and download handling

---
## Future Improvements

- Support for additional file types
- Drag-and-drop file upload
- Compression statistics display
- Progress indicator during compression
- Support for larger file sizes

---
## Author

Harsha Kandpal

GitHub: https://github.com/Kandpalharsha
