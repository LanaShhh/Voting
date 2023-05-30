import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Page as PageCreatePoll } from './pages/poll/create';

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path='poll/create' element={<PageCreatePoll />} />
        </Routes>
      </BrowserRouter>
  );
}


export default App;
