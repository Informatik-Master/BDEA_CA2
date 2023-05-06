import React, { useState, useEffect } from "react";
import FileService from "../api/services";
import toast from "react-hot-toast";

function GlobalTagCLoudComponent() {
  const [data, setData] = useState(undefined);

  useEffect(() => {
    const refreshImage = async () => {
      try {
        const response = await FileService.getWordCloudFromAllFiles();
        let url = URL.createObjectURL(response.data);
        setData(url);
      } catch {}
    };
    refreshImage();
    var i = setInterval(() => {
      refreshImage();
    }, 10_000);
    return () => clearInterval(i);
  }, []);

  const handleButtonClick = async () => {
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
    await FileService.createWordCloudFromAllFiles();
  };

  return (
    <div className="card">
      <div
        className="card-header"
        style={{ display: "flex", flexDirection: "column" }}
      >
        <h1>Global Tag Cloud</h1>
        {
          <button
            className="btn btn-primary btn-sm mt-3"
            type="submit"
            onClick={handleButtonClick}
          >
            Run Tag Cloud Batch
          </button>
        }
      </div>
      <div className="card-body">
        {data ? (
          <img src={data} alt="Tag Cloud" />
        ) : (
          <span>No global word cloud available yet.</span>
        )}
      </div>
    </div>
  );
}

export default GlobalTagCLoudComponent;
