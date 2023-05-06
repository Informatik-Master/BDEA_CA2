import React, { useState } from "react";
import FileService from "../api/services";
import toast from "react-hot-toast";

function DocFreqComponent() {
  const [word, setWord] = useState(""); // word: input value
  const [docFreq, setDocFreq] = useState(0); // docFreq: output value
  const [resultIsReady, setResultIsReady] = useState(false);

  const handleInputChange = (event) => {
    setWord(event.target.value.toUpperCase());
    setResultIsReady(false);
  };

  const search = async () => {
    const response = await FileService.getDocFrequency(word);
    setDocFreq(response.data.frequency || 0);
    setResultIsReady(true);
  };

  const handleButtonClick = async () => {
    await FileService.getBatchDocFrequency();
    toast("Batch triggered", {
      duration: 4000,
      position: "top-right",
      icon: "✅",
      style: {
        padding: "15px",
      },
      iconTheme: {
        primary: "#000",
        secondary: "#fff",
      },
    });
  };

  return (
    <div className="card">
      <div
        className="card-header"
        style={{ display: "flex", flexDirection: "column" }}
      >
        <h1>Document Frequency</h1>
        {
          <button
            className="btn btn-primary btn-sm mt-3"
            type="submit"
            onClick={handleButtonClick}
          >
            Run Document Frequency Batch
          </button>
        }
      </div>
      <div className="card-body">
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Searchterm..."
            value={word}
            onChange={handleInputChange}
          />
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
            onClick={search}
          >
            Search
          </button>
        </div>
        {resultIsReady && docFreq === 0 && (
          <div className="mt-3">The word "{word}" does not appear in any documents.</div>
        )}
        {resultIsReady && docFreq > 0 && (
          <div className="mt-3">
            The word "{word}" appears in {docFreq} document(s).
          </div>
        )}
      </div>
    </div>
  );
}

export default DocFreqComponent;
