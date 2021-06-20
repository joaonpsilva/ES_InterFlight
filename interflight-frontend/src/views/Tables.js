/*!

=========================================================
* Paper Dashboard React - v1.3.0
=========================================================

* Product Page: https://www.creative-tim.com/product/paper-dashboard-react
* Copyright 2021 Creative Tim (https://www.creative-tim.com)

* Licensed under MIT (https://github.com/creativetimofficial/paper-dashboard-react/blob/main/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";

// reactstrap components
import {
  Card,
  CardHeader,
  CardBody,
  CardTitle,
  Table,
  Row,
  Col,
} from "reactstrap";
import historyservice from "services/HistoryService";

class Tables extends React.Component {
  constructor() {
    super()
    this.state = {
      flights: []
    }
  }
  componentDidMount(){
    historyservice.getFlights().then((Response)=>{
      this.setState({flights:Response.data})
    }) ;
  }  
     
 
  render(){
      return(
      <>
      <div className="content">
        <Row>
          <Col md="12">
            <Card>
              <CardHeader>
                <CardTitle tag="h4">Previous Flights</CardTitle>
              </CardHeader>
              <CardBody>
                <Table responsive>
                  <thead className="text-primary">
                    <tr>
                      <th>Date</th>
                      <th>Icao24</th>
                      <th>Origin Country</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      this.state.flights.map(
                        flight =>
                        <tr key = {flight.id}>
                          <td>{flight.date}</td>
                          <td>{flight.icao24}</td>
                          <td>{flight.originCountry}</td>
                        </tr>
                      )  
                    }
                  </tbody>
                </Table>
              </CardBody>
            </Card>
          </Col>
          
        </Row>
      </div>
     </>
    );
  }
}

export default Tables;
