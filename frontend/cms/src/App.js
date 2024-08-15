
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import React from 'react'
import Navbar from './Component/Navbar/Navbar'
import CustomerTable from './Component/C_Dashboard/CustomerTable'
import AddCustomerForm from './Component/CreateCustomer/AddCustomerForm'
import Login from './Component/Auth/Login'

import Register from "./Component/Auth/Register";
import UpdateCustomer from "./Component/Update/UpdateCustomer";

const App = () => {
  return (
    <>
    <Navbar/>
    {/* <CustomerTable/>
    <AddCustomerForm/> */}

    <div className='app'>
      <Router>
<Routes>

<Route path="/navbar" element={<Navbar/>} ></Route>
<Route path="ctable" element = {<CustomerTable/>}></Route>
<Route path="form" element = {<AddCustomerForm/>}></Route>
<Route path='/login' element={<Login/> } />
          <Route path='/register' element={<Register /> } />
          <Route path='/update/:id' element={<UpdateCustomer /> } />

</Routes>


      </Router>



    </div>

    </>
  )
}

export default App

