import { Route, Routes } from 'react-router-dom';
import './App.css';
import CustomerRoutes from './Routers/CustomerRoutes';
import AdminPannel from './Admin/AdminPannel';

function App() {
  // const isAdmin=true;

  
  return (
    <div className="">
      
      <Routes>
        <Route path="/*" element={<CustomerRoutes />} />
        <Route path="/ad/min/*" element={<AdminPannel />} />
        
      </Routes>
    </div>
  );
}

export default App;

