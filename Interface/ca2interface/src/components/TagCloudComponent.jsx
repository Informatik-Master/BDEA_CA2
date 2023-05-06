import React, {useState, useEffect} from "react";
import FileService from '../api/services';

function TagCloudComponent(props) {
    const [data, setData] = useState([]);
    useEffect(() => {
      if (!props.source) return;
          FileService.getTagCloud(props.source).then((response) => {
            let url = URL.createObjectURL(response.data)
            setData(url);
          });
    }, []);

    return (
        <img src={data} alt="Tag Cloud" style={{height: '100%', width: '100%'}} />
    );
}
  
export default TagCloudComponent;
  