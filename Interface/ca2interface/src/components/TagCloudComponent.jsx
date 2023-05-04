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

    if (!data) {
      return <div>Waiting for data</div>;
    }
    return (
      <div>
        <img src={data} alt="Tag Cloud" />
      </div>
    );
}
  
export default TagCloudComponent;
  