import React, {useState, useEffect} from "react";
import { Buffer } from "buffer";
import FileService from '../api/services';

function TagCloudComponent() {

    const [data, setData] = useState([]);

    useEffect(() => {  
      const getTagCloud = async () => {
        try
        {
          FileService.getTagCloud().then((response) => {
            let blob = new Blob([response.data], { type: response.headers['content-type'] })
            let url = URL.createObjectURL(blob)
            setData(url);
          //const buffer = Buffer.from(response.data, 'binary').toString('base64');
          //setData('data:image.png;base64,${buffer}');
          });
        }
        catch(err)
        {
          console.log(err);
        }
      };
      getTagCloud();
    }, []);

    if (!data) {
      return <div>Waiting for data</div>;
    }
    return (
      <div>
        <img src={data + '.PNG'} alt="Tag Cloud" />
      </div>
    );
}
  
export default TagCloudComponent;
  