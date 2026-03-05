import { useState } from "react";
import { FaUpload, FaCompressAlt, FaFileArchive } from "react-icons/fa";
import "./App.css";

function App() {
    const [originalFile, setOriginalFile] = useState(null);
    const [compressedFile, setCompressedFile] = useState(null);

    const compressFile = async () => {
        if (!originalFile) return alert("Select file first");

        const formData = new FormData();
        formData.append("file", originalFile);

        const res = await fetch("http://localhost:8080/api/compress", {
            method: "POST",
            body: formData
        });

        const blob = await res.blob();
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "compressed.huff";
        a.click();
    };

    const decompressFile = async () => {
        if (!compressedFile) return alert("Select .huff file");

        const formData = new FormData();
        formData.append("file", compressedFile);

        const res = await fetch("http://localhost:8080/api/decompress", {
            method: "POST",
            body: formData
        });

        const blob = await res.blob();
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "decompressed.txt";
        a.click();
    };

    return (
        <div className="app">

            <div className="card">

                <h1>Huffman File Compressor</h1>

                {/* COMPRESS SECTION */}
                <div className="section">

                    <h2>Compress File</h2>

                    <label className="upload">
                        <FaUpload />
                        Upload Original File
                        <input
                            type="file"
                            onChange={(e) => setOriginalFile(e.target.files[0])}
                        />
                    </label>

                    {originalFile && (
                        <p className="filename">{originalFile.name}</p>
                    )}

                    <button className="btn compress" onClick={compressFile}>
                        <FaCompressAlt /> Compress
                    </button>

                </div>

                <div className="divider"></div>

                {/* DECOMPRESS SECTION */}
                <div className="section">

                    <h2>Decompress File</h2>

                    <label className="upload">
                        <FaFileArchive />
                        Upload .huff File
                        <input
                            type="file"
                            onChange={(e) => setCompressedFile(e.target.files[0])}
                        />
                    </label>

                    {compressedFile && (
                        <p className="filename">{compressedFile.name}</p>
                    )}

                    <button className="btn decompress" onClick={decompressFile}>
                        Decompress
                    </button>

                </div>

            </div>
        </div>
    );
}

export default App;