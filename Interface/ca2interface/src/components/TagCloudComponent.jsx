import React, {useState, useEffect} from "react";
import FileService from '../api/services';

function TagCloudComponent() {

    const [data, setData] = useState([]);

    useEffect(() => {  
        getTagCloud();  //function is loaded by first render
      }, []);
  
    const getTagCloud = async () => {
  
      try
      {
        FileService.getTagCloud().then((response) => {
        setData(response.data); 
        console.log(response.data);  
        });
      }
      catch(err)
      {
        console.log(err);
      }
    };      
  
    return (
      <div>
        <img src={data} alt="Tag Cloud" />
      </div>
    );
}
  
export default TagCloudComponent;
  