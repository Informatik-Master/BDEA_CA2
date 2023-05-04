import api from './axiosConfig';

class FileService {

  uploadFile = (file) => api.post(`/lambda/upload`, file);

  getTagCloud = (docId) => api.get(`/warehouse/documents/${docId}/wordcloud`, {responseType: 'blob'});

  getDocFrequency = (word) => api.get(`/warehouse/globalWordFrequency/${word}`);

   /*createWordCloudFromAllFiles = () =>{
      return api.post('/batch/wordcloud');
  }*/
}
  
export default new FileService();