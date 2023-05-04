import React, { useState } from 'react';
import FileService from '../api/services';
import TagCloudComponent from './TagCloudComponent';

const FilesUploadComponent = () => {
    const [selectedFiles, setSelectedFile] = useState([]);
    const [fileList, setFileList] = useState([]);

    const handleFileChange = (event) => {
        setSelectedFile([...selectedFiles, ...event.target.files]);
    };
    
    const handleFileUpload = () => {
        const formData = new FormData();
        for (let file of selectedFiles) {
            formData.append("uploadMultipart", file);
        }
        
        //formData.append("uploadMultipart", selectedFiles);
        FileService.uploadFile(formData).then((response) => {
            setFileList([...fileList, response.data]);
            setSelectedFile([]);
        });
    };
    
      return (
        <div className='row'>
            <div className='card col-md-6 offset-md-3 mt-5'>
                <h3 className='text-center'>Upload File</h3>
                    <div className='card-body'>
                        <div>
                            <label>Select files:</label>
                            <input className='mx-2' type='file' name='file' onChange={handleFileChange} multiple></input>
                        </div>
                        <button className='btn btn-success btn-sm mt-3' type='submit' onClick={handleFileUpload}>Upload</button>
                    </div>
                <div className="card">
                    <div className="card-header">List of uploaded files</div>
                    <ul style={{ listStyle: 'none' }}>
                        {fileList.map((doc, i) => (
                            <li key={doc.id}>{doc.filename}
                                <TagCloudComponent source={doc.id} />
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
        );
    };
    
export default FilesUploadComponent;