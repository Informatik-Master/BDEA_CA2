import './App.css';
import HeaderComponent from './components/HeaderComponent';
import { Routes, Route} from 'react-router-dom';
import DocFreqComponent from './components/DocFreqComponent';
import FilesUploadComponent from './components/FilesUploadComponent';
import GlobalTagCLoudComponent from './components/GlobalTagCloudComponent';


function App() {  

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<HeaderComponent />}/>
        <Route path="/upload" element={<GlobalTagCLoudComponent />}/>
        <Route path="/tag-clouds" element={<DocFreqComponent />}/>
      </Routes>
    </div>
    );
  }

export default App;
