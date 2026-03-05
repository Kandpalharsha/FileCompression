package com.compressor.controller;

import com.compressor.service.HuffmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CompressionController {

    @Autowired
    private HuffmanService service;

    @PostMapping("/compress")
    public ResponseEntity<byte[]> compressFile(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] compressed = service.compress(file.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("compressed.huff").build());

        return new ResponseEntity<>(compressed, headers, HttpStatus.OK);
    }

    @PostMapping("/decompress")
    public ResponseEntity<byte[]> decompressFile(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] decompressed = service.decompress(file.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
                ContentDisposition.attachment().filename("decompressed_output").build());

        return new ResponseEntity<>(decompressed, headers, HttpStatus.OK);
    }
    @GetMapping("/")
    public String home() {
        return "Huffman File Compression API is running!";
    }


}
