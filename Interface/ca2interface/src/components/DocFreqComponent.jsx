import React, { useState } from 'react';
import FileService from '../api/services';
import DocFreqBatchComponent from './DocFreqBatchComponent';

function DocFreqComponent() {
  const [word, setWord] = useState(''); // word: input value
  const [docFreq, setDocFreq] = useState(0); // docFreq: output value
  const [resultIsReady, setResultIsReady] = useState(false);

  const handleInputChange = (event) => {  
    setWord(event.target.value.toUpperCase());
    setResultIsReady(false);
  };

  const handleButtonClick = async () => { 
    FileService.getDocFrequency(word).then((response) => {
      setDocFreq(response.data.frequency || 0);
      setResultIsReady(true);
      }); 
    };

  return (
    <div>
      <DocFreqBatchComponent />
      <br></br>
      <input type="text" value={word} onChange={handleInputChange} />
      <button onClick={handleButtonClick}>Refresh Document Frequency</button>
          {resultIsReady && docFreq === 0 && <p>The word "{word}" does not appear in any documents.</p>}
          {resultIsReady && docFreq > 0 && <p>The word "{word}" appears in {docFreq} document(s).</p>}
    </div>
  );
}

export default DocFreqComponent;
