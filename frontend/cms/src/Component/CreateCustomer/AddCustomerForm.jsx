import React, { useState } from 'react';
import './addcustomerform.css';
import { Link, useNavigate } from 'react-router-dom';



const AddCustomerForm = () => {
    const [customer, setCustomer] = useState({
        first_name :"",
        last_name:"",
        street:"",
        address:"",
        city:"",
        state:"",
        email:"",
        phone:""
    });
   
    const navigate = useNavigate();
    const handleChange = (e) => {
        // console.log("Customer Saved:", { firstName, lastName, email, });
        const value = e.target.value;
        setCustomer({...customer, [e.target.name]:value});
        // Add save logic here
    };

const handleSubmit = async(e)=>{
    e.preventDefault();
    try {
        const response = await fetch(
          "http://localhost:8072/api/v1/customers/public/create",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(customer),
          }
        );
  
        if (!response.ok) {
          throw new Error("Failed to save customer");
        } else {
          console.log("Customer saved successfully");
          navigate("/ctable");
        }

         // Clear form fields
      setCustomer({
        first_name: "",
        last_name: "",
        street: "",
        address: "",
        city: "",
        state: "",
        email: "",
        phone: "",
      });
    } catch (error) {
      console.error("Error saving customer:", error);
    }
}

    const handleClear = (e) => {
       e.preventDefault();
       setCustomer({ 
        first_name :"",
        last_name:"",
        street:"",
        address:"",
        city:"",
        state:"",
        email:"",
        phone:""
    });
    };

   

    return (
        <div className="form-container">
            <h2>Add <span role="img" aria-label="add-customer">👤</span> New Customer</h2>
            <form>
                <input 
                    type="text" 
                    placeholder="First Name" 
                    value={customer.first_name} 
                    name='first_name'
                    onChange={(e)=>handleChange(e)}
                />
                <input 
                    type="text" 
                    placeholder="Last Name" 
                    value={customer.last_name} 
                    name='last_name'
                    onChange={(e)=>handleChange(e)}
                />
                 <input 
                    type="text" 
                    placeholder="Street" 
                    value={customer.street} 
                    name='street'
                    onChange={(e)=>handleChange(e)}
                />
                <input 
                    type="text" 
                    placeholder="Address" 
                    value={customer.address} 
                    name='address'
                    onChange={(e)=>handleChange(e)}
                />
                 <input 
                    type="text" 
                    placeholder="City" 
                    value={customer.city} 
                    name='city'
                    onChange={(e)=>handleChange(e)}
                />
                <input 
                    type="text" 
                    placeholder="State" 
                    value={customer.state} 
                    name='state'
                    onChange={(e)=>handleChange(e)}
                />
               
                <input 
                    type="number" 
                    placeholder="Mobile Number" 
                    value={customer.phone} 
                    name='phone'
                    onChange={(e)=>handleChange(e)}
                />
                <input 
                    type="email" 
                    placeholder="Email" 
                    value={customer.email} 
                    name='email'
                    onChange={(e)=>handleChange(e)}
                />
                <div className="form-buttons">
                    <button type="button" className="save-btn" onClick={handleSubmit} >Save</button>
                    <button type="button" className="clear-btn" onClick={handleClear}>Clear</button>
                  <Link to="/ctable"> <button type="button" className="cancel-btn" >Cancel</button> </Link> 
                </div>
            </form>
        </div>
    );
};

export default AddCustomerForm;
