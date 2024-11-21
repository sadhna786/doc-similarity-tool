import React, { useState } from "react";
import axios from "axios";

function CompareDocuments() {
    const [content1, setContent1] = useState("");
    const [content2, setContent2] = useState("");
    const [comparisonResult, setComparisonResult] = useState("");

    const handleCompare = async () => {
        try {
            const response = await axios.post(
                "http://localhost:8080/api/documents/compare",
                {
                    content1,
                    content2,
                }
            );
            setComparisonResult(response.data);  // Display the similarity result
        } catch (error) {
            console.error("Error comparing documents:", error);
            setComparisonResult("Error comparing documents.");
        }
    };
    

    return (
        <div style={{ padding: "20px" }}>
            <h1>Document Comparison Tool</h1>
            <div>
                <textarea
                    value={content1}
                    onChange={(e) => setContent1(e.target.value)}
                    placeholder="Enter Document 1"
                    rows={10}
                    cols={50}
                    style={{ margin: "10px", padding: "10px" }}
                />
                <textarea
                    value={content2}
                    onChange={(e) => setContent2(e.target.value)}
                    placeholder="Enter Document 2"
                    rows={10}
                    cols={50}
                    style={{ margin: "10px", padding: "10px" }}
                />
            </div>
            <button
                onClick={handleCompare}
                style={{
                    padding: "10px 20px",
                    margin: "10px",
                    backgroundColor: "#007BFF",
                    color: "#fff",
                    border: "none",
                    borderRadius: "5px",
                    cursor: "pointer",
                }}
            >
                Compare
            </button>
            <div>
                <h3>Comparison Result:</h3>
                <div
                    style={{
                        border: "1px solid #ccc",
                        padding: "10px",
                        backgroundColor: "#f9f9f9",
                        marginTop: "10px",
                    }}
                    dangerouslySetInnerHTML={{ __html: comparisonResult }}
                ></div>
            </div>
        </div>
    );
}

export default CompareDocuments;
