import React, { useState, useEffect } from 'react';
import './updatecustomer.css';
import { Link, useNavigate, useParams } from 'react-router-dom';


const UpdateCustomer = () => {

    const {id} = useParams();
    const [customer, setCustomer] = useState({
        id: id,
        first_name :"",
        last_name:"",
        street:"",
        address:"",
        city:"",
        state:"",
        email:"",
        phone:""
    });
   
   
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCustomer = async () => {
          try {
            const response = await fetch(
              `http://localhost:8072/api/v1/customers/public/${id}`,
              {
                method: "GET",
                headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${localStorage.getItem("authToken")}`,
                },
              }
            );
            if (!response.ok) {
              throw new Error("Error fetching customer details");
            }
            const data = await response.json();
            setCustomer(data);
            setLoading(false);
          } catch (error) {
            setError(error);
            setLoading(false);
          }
        };
    
        fetchCustomer();
      }, [id]);


  
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
          `http://localhost:8072/api/v1/customers/public/${id}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("authToken")}`,
            },
            body: JSON.stringify(customer),
          }
        );
  
        if (!response.ok) {
          throw new Error("Error updating customer");
        }
  
        navigate("/ctable"); // Redirect to customers list after update
      } catch (error) {
        setError(error);
      }
}

if (loading) return <div>Loading...</div>;
if (error) return <div className="error-message">Error: {error.message}</div>;

   

    return (
        <div className="form-container">
            <h2>Update <span role="img" aria-label="add-customer">👤</span> Customer</h2>
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
                    <button type="button" className="save-btn" onClick={handleSubmit} >Update</button>
                  <Link to="/ctable"> <button type="button" className="cancel-btn" >Cancel</button> </Link> 
                </div>
            </form>
        </div>
    );
};

export default UpdateCustomer;
