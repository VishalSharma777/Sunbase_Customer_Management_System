
import React, { useEffect, useState , useCallback} from 'react';
import './customertable.css';
import { Link ,  useNavigate } from 'react-router-dom';
import debounce from "lodash.debounce";


const CustomerTable = () => {


    const [customers, setCustomers] = useState([]);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sort, setSort] = useState("id");
  const [search, setSearch] = useState("");
  const [error, setError] = useState(null);

    const token = localStorage.getItem("authToken");
    console.log("token>>>>>",token);

    const fetchCustomers = useCallback(
        debounce(async () => {
          setLoading(true);
          setError(null);
          try {
            const response = await fetch(
              `http://localhost:8072/api/v1/customers/public/customerList?search=${search}&page=${page}&size=${size}&sort=${sort}`,
              {
                method: "GET",
                headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${token}`,
                },
              }
            );

            if (!response.ok) {
                throw new Error("Error fetching customers");
              }
      
              const data = await response.json();
              setCustomers(data.content);
            } catch (error) {
              setError(error.message);
            } finally {
              setLoading(false);
            }
          }, 300),
          [search, page, size, sort, token]
        );

        useEffect(() => {
            fetchCustomers();
          }, [fetchCustomers]);

    
 
   


  const  deleteCustomer= async (id) => {
        try {
          const response = await fetch(
            `http://localhost:8072/api/v1/customers/public/${id}`,
            {
              method: "DELETE",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
            }
          );
    
          if (!response.ok) {
            throw new Error("Error deleting customer");
          }
    
          fetchCustomers();
        } catch (error) {
          setError(error.message);
        }
      
        
    };


    const handleScnc = async () => {
        try {
          const response = await fetch(
            "http://localhost:8072/api/v1/customers/public/usersList",
            {
              method: "GET",
              headers: {
                "Content-Type": "application/json",
               // Authorization: `Bearer ${token}`,
              },
            }
          );
          if (!response.ok) {
            throw new Error("Error fetching customers");
          }
          console.log(">>>>>>>", await response.json());
        } catch (error) {
          setError(error.message);
        }
      };

      const handleSortChange = (e) => {
        setSort(e.target.value);
      };
      const  UpdateCustomer = (e,id)=>{
        e.preventDefault();
        navigate(`/update/${id}`);
       }
      const handleSearch = (e) => {
        setSearch(e.target.value);
      };
    
      if (loading) return <div>Loading...</div>;
      if (error) return <div>Error: {error}</div>;

 
    return (
        <div className="table-container">
            <div className="table-top">
            <Link to="/form">
                <button className="add-customer-btn">
                    Add Customer <span role="img" aria-label="add-customer">👤</span>
                </button>
            </Link>
            <div className="search">
          <input
            className="search"
            type="text"
            placeholder="Search by name"
            value={search}
            onChange={handleSearch}
          />
          <button className="add-customer-btn" onClick={fetchCustomers}>
            Search
          </button>
        </div>
        <div>
          <button className="add-customer-btn" onClick={handleScnc}>
            Sync
          </button>
        </div>
        <div className="filter-container">
          <label className="sort" htmlFor="filter">
            Sort by:
          </label>
          <select
            id="filter"
            className="add-customer-btn"
            value={sort}
            onChange={handleSortChange}
          >
            <option value="firstName">First Name</option>
            <option value="id">ID</option>
            <option value="city">City</option>
          </select>
        </div>
        </div>

            <table className="customer-table">
                <thead>
                    <tr>
                        <th>FIRST NAME</th>
                        <th>LAST NAME</th>
                        <th>EMAIL ID</th>
                        <th>STREET</th>
                        <th>ADDRESS</th>
                        <th>CITY</th>
                        <th>STATE</th>
                        <th>PHONE</th>
                        <th>ACTIONS</th>
                    </tr>
                </thead>
               
                    <tbody>
                        {customers && customers.length > 0 ? (
                            customers.map((customer, index) => (
                                <tr key={index}>
                                    <td>{customer.first_name}</td>
                                    <td>{customer.last_name}</td>
                                    <td>{customer.email}</td>
                                    <td>{customer.street}</td>
                                    <td>{customer.address}</td>
                                    <td>{customer.city}</td>
                                    <td>{customer.state}</td>
                                    <td>{customer.phone}</td>
                                    <td>
                                        <a href="#" className="edit-link" onClick={(e,id)=>UpdateCustomer(e,customer.id)}>Edit</a>
                                        <a href="#" className="delete-link" onClick={()=>{deleteCustomer(customer.id)}}>Delete</a>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="9">No customers found</td>
                            </tr>
                        )}
                    </tbody>
                
            </table>

            <div className="below">
        <button
          className="add-customer-btn"
          onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
          disabled={page === 0}
        >
          Previous
        </button>
        <button
          className="add-customer-btn"
          onClick={() => setPage((prev) => prev + 1)}
        >
          Next
        </button>
      </div>
            
        </div>
    );
};

export default CustomerTable;
