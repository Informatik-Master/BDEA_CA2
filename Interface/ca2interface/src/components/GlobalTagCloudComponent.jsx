import React, { useState, useEffect } from "react";
import FileService from "../api/services";

function GlobalTagCLoudComponent() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const refreshImage = () => {
      FileService.getWordCloudFromAllFiles().then((response) => {
        let url = URL.createObjectURL(response.data);
        setData(url);
      });
    };
    refreshImage();
    setInterval(() => {
      refreshImage();
    }, 10_000);
  }, []);

  const handleButtonClick = () => FileService.createWordCloudFromAllFiles();

  return (
    <div>
      <h3 className="text-center">Global Tag Cloud</h3>
      <br></br>
      <button
        className="btn btn-success btn-sm mt-3"
        type="submit"
        onClick={handleButtonClick}
      >
        Get Global Tag Cloud
      </button>
      <div>
        {data ? (
          <img src={data} alt="Tag Cloud" />
        ) : (
          <p>Waiting for tag cloud...</p>
        )}
      </div>
    </div>
  );
}

export default GlobalTagCLoudComponent;
