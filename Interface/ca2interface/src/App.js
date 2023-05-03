import './App.css';
import HeaderComponent from './components/HeaderComponent';
import { Routes, Route} from 'react-router-dom';
import HeaderTagCloudComponent from './components/HeaderTagCloudComponent';
import DocFreqComponent from './components/DocFreqComponent';


function App() {  

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<HeaderComponent />}/>
        <Route path="/upload" element={<HeaderTagCloudComponent />}/>
        <Route path="/tag-clouds" element={<DocFreqComponent />}/>
      </Routes>
    </div>
    );
  }

export default App;
