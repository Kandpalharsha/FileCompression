import axios from "axios";
import { useState } from "react";

function Compressor() {
    const [file, setFile] = useState(null);

    const upload = async () => {
        const formData = new FormData();
        formData.append("file", file);

        const response = await axios.post(
            "http://localhost:8080/api/compress",
            formData,
            { responseType: "blob" }
        );

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "compressed.huff");
        document.body.appendChild(link);
        link.click();
    };


    const decompress = async () => {
        const formData = new FormData();
        formData.append("file", file);

        const response = await axios.post(
            "http://localhost:8080/api/decompress",
            formData,
            { responseType: "blob" }
        );

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "decompressed_output.txt");
        document.body.appendChild(link);
        link.click();
    };


    return (
        <div>
            <h1>Huffman File Compressor</h1>
            <input type="file" onChange={(e) => setFile(e.target.files[0])} />
            <button onClick={upload}>Compress</button>
            <button onClick={decompress}>Decompress</button>

        </div>
    );
}

export default Compressor;
