import api from './axiosConfig';

class FileService {

    getTagCloud = async () =>{
        try
        {
          const response = await api.get('/doc'); 
          return response;
        } 
        catch(err)
        {
          console.log(err);
        }
      }
    
    uploadFile = (file) =>{
        return api.post('/upload', file);	
    }

    createWordCloudFromAllFiles = () =>{
        return api.post('/batch/all');
    }

    createDocFrequency = () =>{
        return api.post('/batch/df');
    }
}
  
export default new FileService();