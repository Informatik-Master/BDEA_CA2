import React, { Component } from 'react';
import { Navigate } from 'react-router-dom';
import FileService from '../api/services';

// upload component
class FilesUploadComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            files: null,
            fileUploaded: null
        }
    }
    onFileChange = (event) => {
        this.setState({files: event.target.files});
    }
    
    onUpload = (event) => {
        event.preventDefault(); // Stop form submit
        const formData = new FormData(); // Create an instance of FormData	
        for (const key of Object.keys(this.state.files)) {
            formData.append('files', this.state.files[key]);}
        //formData.append('files', this.state.files[0]);
        FileService.uploadFile(formData).then((response) => {
            console.log(response.data);
            this.setState({ fileUploaded: true });
        }).catch(error => {
            console.log(error);
        });
    }
    render() {
        if(this.state.fileUploaded){
            return  <Navigate to="/my-images" replace={true} />;
        }
        return (
            <div className='row'>
                <div className='card col-md-6 offset-md-3 mt-5'>
                    <h3 className='text-center'>Upload File</h3>
                    <div className='card-body'>
                        <form onSubmit={this.onUpload}>
                            <div>
                                <label>Select a file:</label>
                                <input className='mx-2' type='file' name='file' onChange={this.onFileChange} multiple></input>
                            </div>
                            <button className='btn btn-success btn-sm mt-3' type='submit' disabled={!this.state.files}>Upload</button>
                        </form>
                    </div>
                    <div className="card">
                        <div className="card-header">List of Files</div>
                            <ul className="list-group list-group-flush">
                                {fileInfos &&
                                    fileInfos.map((file, index) => (
                                        <li className="list-group-item" key={index}>
                                            <a href={file.url}>{file.name}</a>
                                        </li>
                                    ))}
                            </ul>
                        </div>
                    </div>
            </div>
        );
    }
}
export default FilesUploadComponent;