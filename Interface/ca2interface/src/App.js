import './App.css';
import HeaderComponent from './components/HeaderComponent';
import { Routes, Route} from 'react-router-dom';
import HeaderTagCloudComponent from './components/HeaderTagCloudComponent';


function App() {  

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<HeaderComponent />}/>
        <Route path="/upload" element={<HeaderTagCloudComponent />}/>
      </Routes>
    </div>
    );
  }

export default App;
