import React, { useState } from 'react';
import FileService from '../api/services';

function DocumentFrequency() {
  const [word, setWord] = useState(''); // word: input value
  const [docFreq, setDocFreq] = useState(0); // docFreq: output value

  const handleInputChange = (event) => {  
    setWord(event.target.value.toUpperCase());
  };

  const handleButtonClick = async () => { 
    FileService.getDocFrequency(word).then((response) => {
      setDocFreq(response.data.frequency);
      setData(url);
      }); 
    };

  return (
    <div>
      <input type="text" value={word} onChange={handleInputChange} />
      <button onClick={handleButtonClick}>Get Document Frequency</button>
      {docFreq > 0 && (
        <p>
          The word "{word}" appears in {docFreq} document(s).
        </p>
      )}
    </div>
  );
}

export default DocumentFrequency;
