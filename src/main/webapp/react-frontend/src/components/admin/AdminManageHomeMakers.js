import React, { useEffect, useState, } from "react"
import { Table } from "reactstrap"
import CustomerService from "../../service/CustomerService"
import { toast } from "react-toastify"
import HomeMakerService from "../../service/HomeMakerService"

import ReactPaginate from "react-paginate";

const AdminManageHomeMakers = () => {

  const [homeMakers, setHomeMakers] = useState([])
  const [reload, setReload] = useState(1)

  useEffect(() => {
    document.title = "Admin || Home Makers"
    allHomeMakers()
  }, [reload])

  const allHomeMakers = () => {
    CustomerService.getAllHomeMakers()
      .then((response) => {
        console.log(response)
        setHomeMakers(response.data.result)
      }, (error) => {
        // for error
        console.log(error)
        toast.error("something went wrong", { position: "bottom-center" })
      })
  }

  const [pageNumber, setPageNumber] = useState(0);

  const homeMakersPerPage = 3;
  const pagesVisited = pageNumber * homeMakersPerPage;

  const displayItems = homeMakers
    .slice(pagesVisited, pagesVisited + homeMakersPerPage)
    .map((homeMaker) => {
      return (
        <tr className="items">
          <td>{homeMaker.id}</td>
          <td>{homeMaker.name}</td>
          <td>{homeMaker.email}</td>
          <td>{homeMaker.phoneNo}</td>
          <td>{homeMaker.city}</td>
          <td>
            <button className="btn btn-danger" onClick={() => removeHomeMaker(homeMaker.id)}> Remove</button>
          </td>
        </tr>
      );
    });

  const pageCount = Math.ceil(homeMakers.length / homeMakersPerPage);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  const removeHomeMaker = (hmId) => {
    console.log(hmId)
    HomeMakerService.deleteUser(hmId).then(response => {
      toast.success(response.data.result, { position: "bottom-center" })
      setReload(reload + 1)
      console.log(reload)
    })
  }

  return (
      <div className="text-center" style={{ background: "darkgray" }}>
        <h3>All Home Makers</h3>
        <p>Manage home makers here...</p>
        <Table hover bordered className="table table-striped table-responsive-lg">
          <thead style={{ background: "#333", color: "white" }}>
            <tr>
              <th>HM Id</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone Number</th>
              <th>City</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {
              homeMakers && displayItems
            }
          </tbody>
        </Table>
        {
          homeMakers &&
          <ReactPaginate
            previousLabel={"Previous"}
            nextLabel={"Next"}
            pageCount={pageCount}
            onPageChange={changePage}
            containerClassName={"paginationBttns"}
            previousLinkClassName={"previousBttn"}
            nextLinkClassName={"nextBttn"}
            disabledClassName={"paginationDisabled"}
            activeClassName={"paginationActive"}
          />
        }
    </div>
  )
}
export default AdminManageHomeMakers