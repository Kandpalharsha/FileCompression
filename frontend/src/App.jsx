import { useState } from "react";
import { FaUpload, FaCompressAlt, FaFileArchive } from "react-icons/fa";
import "./App.css";

const API_URL = "https://filecompression-561i.onrender.com";

function App() {

    const [originalFile, setOriginalFile] = useState(null);
    const [compressedFile, setCompressedFile] = useState(null);
    const [loading, setLoading] = useState(false);
    const [stats, setStats] = useState(null);

    const compressFile = async () => {

        if (!originalFile) return alert("Select file first");

        const formData = new FormData();
        formData.append("file", originalFile);

        setLoading(true);

        const res = await fetch(`${API_URL}/api/compress`, {
            method: "POST",
            body: formData
        });

        const blob = await res.blob();

        setLoading(false);

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "compressed.huff";
        a.click();

        setStats({
            original: originalFile.size,
            compressed: blob.size
        });

    };

    const decompressFile = async () => {

        if (!compressedFile) return alert("Select .huff file");

        const formData = new FormData();
        formData.append("file", compressedFile);

        setLoading(true);

        const res = await fetch(`${API_URL}/api/decompress`, {
            method: "POST",
            body: formData
        });

        const blob = await res.blob();

        setLoading(false);

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "decompressed.txt";
        a.click();

    };

    return (

        <div className="app">

            <header className="hero">
                <h1>File Compression Tool</h1>

            </header>

            <div className="card">

                {/* Compress Section */}

                <div className="section">

                    <h2>Compress File</h2>

                    <label className="dropzone">

                        <FaUpload size={20} />

                        <p>Drag & Drop or Click to Upload</p>

                        <input
                            type="file"
                            onChange={(e) => setOriginalFile(e.target.files[0])}
                        />

                    </label>

                    {originalFile && (
                        <p className="filename">{originalFile.name}</p>
                    )}

                    <button
                        className="btn compress"
                        onClick={compressFile}
                        disabled={loading}
                    >

                        {loading ? "Compressing..." : <><FaCompressAlt /> Compress</>}

                    </button>

                </div>

                {/* Stats */}

                {stats && (

                    <div className="stats">

                        <p>Original Size: {Math.round(stats.original / 1024)} KB</p>

                        <p>Compressed Size: {Math.round(stats.compressed / 1024)} KB</p>

                        <p>
                            Compression Ratio:
                            {" "}
                            {Math.round(
                                (1 - stats.compressed / stats.original) * 100
                            )} %
                        </p>

                    </div>

                )}

                <div className="divider"></div>

                {/* Decompress Section */}

                <div className="section">

                    <h2>Decompress File</h2>

                    <label className="dropzone">

                        <FaFileArchive size={20} />

                        <p>Upload .huff File</p>

                        <input
                            type="file"
                            onChange={(e) => setCompressedFile(e.target.files[0])}
                        />

                    </label>

                    {compressedFile && (
                        <p className="filename">{compressedFile.name}</p>
                    )}

                    <button
                        className="btn decompress"
                        onClick={decompressFile}
                        disabled={loading}
                    >

                        {loading ? "Processing..." : "Decompress"}

                    </button>

                </div>

            </div>

        </div>

    );

}

export default App;