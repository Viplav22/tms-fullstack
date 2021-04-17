import React, { useEffect, useState } from "react"
import { Table } from "reactstrap"
import OrderService from "../../service/OrderService"
import { toast } from "react-toastify"
import SessionService from "../../service/SessionService"

import ReactPaginate from "react-paginate";

const CustomerOrders = () => {

  const [user] = useState(SessionService.getCurrentUser)
  const [orders, setOrders] = useState([])

  useEffect(() => {
    document.title = "Customer || My Orders"
    OrderService.ofCustomer(user.id)
      .then(response => {
        console.log(response)
        setOrders(response.data.result)
      }, (error) => {
        // for error
        console.log(error)
        toast.error("something went wrong", { position: "bottom-center" })
      })

  }, [user.id])

  const [pageNumber, setPageNumber] = useState(0);

  const ordersPerPage = 3;
  const pagesVisited = pageNumber * ordersPerPage;

  const displayItems = orders
    .slice(pagesVisited, pagesVisited + ordersPerPage)
    .map((order, index) => {
      return (
        <tr key={index} className="items">
          <td>{order.orderId}</td>
          <td>{order.paymentId}</td>
          <td>{order.dateTime}</td>
          <td>{order.receipt}</td>
          <td>{order.amount / 100}</td>
          <td>{order.status}</td>
          <td>{order.customerId}</td>
          <td>{order.homeMakerId}</td>
        </tr>
      );
    });

  const pageCount = Math.ceil(orders.length / ordersPerPage);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };


  return (
    <div className="container text-center">
      <div style={{ background: "darkgray" }}>

        <h3>All Orders : </h3>
        <Table hover bordered className="table table-striped table-responsive-xl">
          <thead style={{ background: "#333", color: "white" }}>
            <tr>
              <th>Order Id</th>
              <th>Payment Id</th>
              <th>Date:Time</th>
              <th>Receipt</th>
              <th>Amount</th>
              <th>Status</th>
              <th>Cus Id</th>
              <th>Hm Id</th>
            </tr>
          </thead>
          <tbody>
            {
              orders && displayItems
            }
          </tbody>
        </Table>
        {
          orders &&
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
    </div>
  )
}
export default CustomerOrders