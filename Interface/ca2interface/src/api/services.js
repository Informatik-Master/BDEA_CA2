import api from './axiosConfig';

class FileService {

  uploadFile = (file) => api.post(`/lambda/upload`, file);

  getTagCloud = (docId) => api.get(`/warehouse/documents/${docId}/wordcloud`, {responseType: 'blob'});

  getDocFrequency = (word) => api.get(`/warehouse/globalWordFrequency/${word}`);

  getBatchDocFrequency = () => api.post(`/batch/documentfrequency`);

  createWordCloudFromAllFiles = () => api.post('/batch/wordcloud');

  getWordCloudFromAllFiles = () => api.get('/warehouse/documents/overall_target', {responseType: 'blob'});

  getPreviouslyUploadedFiles = () => api.get('/warehouse/documents');
}
  
export default new FileService();