import React, { useState } from 'react';
import FileService from '../api/services';

function DocFreqBatchComponent() { 

    const handleButtonClick = () => { 
      FileService.getBatchDocFrequency().then((response) => {
        console.log(response.data);
      }); 
    };
  
    return (
      <div>
        <button className='btn btn-success btn-sm mt-3' type='submit' onClick={handleButtonClick}>Run Batch for Document Frequency</button>
      </div>
    );
  }
  
  export default DocFreqBatchComponent;