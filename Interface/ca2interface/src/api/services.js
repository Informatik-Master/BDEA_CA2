import api from './axiosConfig';

class FileService {

  uploadFile = (file) =>{
    return api.post('/lambda/upload', file);	
  }

  getTagCloud = async () =>{
      try
      {
        const response = await api.get('/warehouse/documents', {responseType: 'arraybuffer'});
        return response;
      } 
      catch(err)
      {
        console.log(err);
      }
    }

  getDocFrequency = async () => {
    try {
      const response = await api.get('/warehouse/documents/${word}');
    return response;
    }
    catch(err){
      console.log(err);
    }
  }

   /*createWordCloudFromAllFiles = () =>{
      return api.post('/batch/wordcloud');
  }*/
}
  
export default new FileService();