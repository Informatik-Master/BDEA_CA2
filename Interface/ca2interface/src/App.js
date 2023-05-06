import "./App.css";
import HeaderComponent from "./components/HeaderComponent";
import { Routes, Route } from "react-router-dom";
import DocFreqComponent from "./components/DocFreqComponent";
import FilesUploadComponent from "./components/FilesUploadComponent";
import GlobalTagCloudComponent from "./components/GlobalTagCloudComponent";
import { Toaster } from 'react-hot-toast';

function App() {
  return (
    <div className="App">
      <HeaderComponent />
      <Toaster/>
      <div className="Content">
        <Routes>
          <Route path="/" element={<FilesUploadComponent />} />
          <Route path="/global" element={<GlobalTagCloudComponent />} />
          <Route path="/freq" element={<DocFreqComponent />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
