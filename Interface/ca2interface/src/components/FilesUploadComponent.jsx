import React, { useState } from 'react';
import FileService from '../api/services';

const FilesUploadComponent = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [fileList, setFileList] = useState([]);

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };
    
    const handleFileUpload = () => {
        const formData = new FormData();
        
        formData.append("file", selectedFile);
        FileService.uploadFile(formData).then((response) => {
            setFileList([...fileList, response.data.fileName]);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    };
    
      return (
        <div className='row'>
                <div className='card col-md-6 offset-md-3 mt-5'>
                    <h3 className='text-center'>Upload File</h3>
                    <div className='card-body'>
                            <div>
                                <label>Select a file:</label>
                                <input className='mx-2' type='file' name='file' onChange={handleFileChange} multiple></input>
                            </div>
                            <button className='btn btn-success btn-sm mt-3' type='submit' onClick={handleFileUpload}>Upload</button>
                    </div>
                    <div className="card">
                        <div className="card-header">List of uploaded files</div>
                            <ul>
                                {fileList.map((fileName) => (
                                    <li key={fileName}>{fileName}</li>
                                ))}
                            </ul>
                        </div>
                    </div>
            </div>
        );
    };
    
export default FilesUploadComponent;