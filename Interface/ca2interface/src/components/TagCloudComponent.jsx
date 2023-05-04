import React, {useState, useEffect} from "react";
import FileService from '../api/services';

function TagCloudComponent(props) {
    const [data, setData] = useState([]);
  console.log(props.source);
    useEffect(() => {  
      console.log(props.source);
      if (!props.source) return;
          FileService.getTagCloud(props.source).then((response) => {
            console.log(response.data);
            // let blob = new Blob([response.data])
            let url = URL.createObjectURL(response.data)
            setData(url);
          //const buffer = Buffer.from(response.data, 'binary').toString('base64');
          //setData('data:image.png;base64,${buffer}');
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
  