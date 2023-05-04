import React, {useState, useEffect} from "react";
import FileService from '../api/services';

function GlobalTagCLoudComponent() {
    const [data, setData] = useState([]);

    const handleButtonClick = async () => { 
    FileService.createWordCloudFromAllFiles().then((response) => {
        let url = URL.createObjectURL(response.data)
        setData(url);
        }); 
    };
  
    return (
        <div>
            <h3 className='text-center'>Global Tag Cloud</h3>
            <br></br>
            <button onClick={handleButtonClick}>Get Global Tag Cloud</button>
            <div>
                <img src={data} alt="Tag Cloud" />
            </div>
        </div>
        
    );
  }

export default GlobalTagCLoudComponent;