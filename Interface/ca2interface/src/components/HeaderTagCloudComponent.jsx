import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import FilesUploadComponent from './FilesUploadComponent';
import TagCloudComponent from './TagCloudComponent';

class HeaderTagCloudComponent extends Component {
    render() {
        return (
            <div>
                <FilesUploadComponent/>
                <TagCloudComponent/>
            </div>
        );
    }
}

export default HeaderTagCloudComponent;