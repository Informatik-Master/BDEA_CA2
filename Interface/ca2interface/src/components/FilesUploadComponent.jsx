import React, { useEffect, useState } from "react";
import { useDropzone } from "react-dropzone";
import BarLoader from "react-spinners/BarLoader";

import FileService from "../api/services";
import TagCloudComponent from "./TagCloudComponent";

const override = {
  position: "absolute",
  width: "100%",
  zIndex: "1000",
};

const FilesUploadComponent = () => {
  const [fileList, setFileList] = useState([]);

  useEffect(() => {
    FileService.getPreviouslyUploadedFiles().then((response) => {
      setFileList(response.data.filter((i) => i.documentType === "SOURCE"));
    });
  }, []);

  const onDrop = async (event) => {
    setLoading(true);
    const formData = new FormData();
    for (let file of event) formData.append("uploadMultipart", file);

    const response = await FileService.uploadFile(formData);
    setFileList([response.data, ...fileList]);
    setLoading(false);
  };
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  let [loading, setLoading] = useState(false);

  return (
    <div
      className="card-list"
      style={{ width: "100%", alignContent: "center" }}
    >
      <div className="card">
        {loading ? (
          <>
            <div className="loader-overlay"></div>
            <BarLoader cssOverride={override} color="var(--bs-blue)" />
          </>
        ) : (
          <></>
        )}

        <div className="card-header">
          <h1>Upload a File</h1>
        </div>

        <div className="card-body">
          <div className="dropzone">
            <div
              className="dropzone-content"
              style={{ cursor: "pointer" }}
              {...getRootProps()}
            >
              <input className="mx-2" {...getInputProps()} accept=".txt" />
              {isDragActive ? (
                <span>Now let go of the file :D</span>
              ) : (
                <span>select or drop a file here</span>
              )}
            </div>
          </div>
        </div>
      </div>

      {fileList.map((doc, i) => (
        <div className="card" key={doc.id}>
          <div className="card-header">
            <h3>{doc.filename}</h3>
          </div>
          <div className="card-body">
            <TagCloudComponent source={doc.id} />
          </div>
        </div>
      ))}
    </div>
    // <div className="row">
    //   <h3 className="text-center">Upload File</h3>
    //   <div className="card-body">
    //     <div>
    //       <label>Select files:</label>
    //       <input
    //         className="mx-2"
    //         type="file"
    //         name="file"
    //         onChange={handleFileChange}
    //         multiple
    //       ></input>
    //     </div>
    //     <button
    //       className="btn btn-success btn-sm mt-3"
    //       type="submit"
    //       onClick={handleFileUpload}
    //     >
    //       Upload
    //     </button>
    //     <br></br>
    //   </div>

    //   <div className="card">
    //     <div className="card-header">List of uploaded files</div>
    //     <ul style={{ listStyle: "none" }}>
    //       {fileList.map((doc, i) => (
    //         <li key={doc.id}>
    //           {doc.filename}
    //           <TagCloudComponent source={doc.id} />
    //         </li>
    //       ))}
    //     </ul>
    //   </div>
    // </div>
  );
};

export default FilesUploadComponent;
