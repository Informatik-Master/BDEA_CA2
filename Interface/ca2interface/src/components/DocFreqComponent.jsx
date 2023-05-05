import React, { useState } from 'react';
import FileService from '../api/services';
import DocFreqBatchComponent from './DocFreqBatchComponent';

function DocFreqComponent() {
  const [word, setWord] = useState(''); // word: input value
  const [docFreq, setDocFreq] = useState(0); // docFreq: output value

  const handleInputChange = (event) => {  
    setWord(event.target.value.toUpperCase());
  };

  const handleButtonClick = async () => { 
    FileService.getDocFrequency(word).then((response) => {
      setDocFreq(response.data.frequency);
      }); 
    };

  return (
    <div>
      <DocFreqBatchComponent />
      <br></br>
      <input type="text" value={word} onChange={handleInputChange} />
      <button onClick={handleButtonClick}>Refresh Document Frequency</button>
          {docFreq == undefined && <p>The word "{word}" does not appear in any documents.</p>}
          {docFreq > 0 && <p>The word "{word}" appears in {docFreq} document(s).</p>}
    </div>
  );
}

export default DocFreqComponent;
