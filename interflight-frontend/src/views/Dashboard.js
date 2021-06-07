
import React from "react";
// react plugin used to create charts
import { Line, Pie } from "react-chartjs-2";
// reactstrap components
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  CardTitle,
  Row,
  Col,
  Table
} from "reactstrap";
// core components

import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
import L from "leaflet"
import connect from "stream.js"



class Dashboard extends React.Component{

  constructor() {
    super();
    this.state = {
      flights: []
    };
    connect("/realtime/getAllPlanes", this)

  }
  
  setFlights(newflights){
    this.setState({flights: newflights})
  }


  handleCountry(){
    connect("/realtime/streamOriginCountry?value=" + document.getElementById("/streamOriginCountry").value, this )
  }
  handleIcao24(){
    connect("/realtime/getPlane?value=" + document.getElementById("/streamAircraft").value, this )
  }

  render(){


    return (
      <>
        <div className="content">
          <Row>
            <Col lg="6" md="6" sm="6">
              <div class="mb-3">
                  <label for="Origin Country" class="form-label">Origin Country</label>

                  <div class="input-group">
                      <input class="form-control" type="text" id="/streamOriginCountry"></input>
                      <span class="input-group-btn">
                          <button class="btn btn-primary" onClick={() => this.handleCountry()} >Search</button>
                      </span>
                  </div>
              </div>
            </Col>
            <Col lg="6" md="6" sm="6">
              <div class="mb-3">
                <label for="Aircraft" class="form-label">Aircraft</label>

                <div class="input-group">
                    <input class="form-control" type="text" id="/streamAircraft"></input>
                    <span class="input-group-btn">
                        <button class="btn btn-primary" onClick={() => this.handleIcao24()} >Search</button>
                    </span>
                </div>
            </div>
            </Col>
            
          </Row>
          <Row>
            <Col md="12">
              <Card>
                
              <MapContainer center={[51.505, -0.09]} zoom={3} scrollWheelZoom={true}>
                <TileLayer
                  attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  
                />
                {this.state.flights.map((flight, idx) => 
                  <Marker key={'marker-${idx}'} position={[flight.latitude, flight.longitude]}>
                </Marker>
                )}
              </MapContainer>

              </Card>
            </Col>
          </Row>
          <Row>
            <Col md="12">
              <Card>
                <CardHeader>
                  <CardTitle tag="h4">Flights</CardTitle>
                </CardHeader>
                <CardBody>
                  <Table responsive>
                    <thead className="text-primary">
                      <tr>
                        <th>Origin Country</th>
                        <th>icao24</th>
                        <th>Last contact</th>
                        <th>Velocity</th>
                      </tr>
                    </thead>
                    <tbody>
                      {this.state.flights.map((flight, idx) =>
                      <tr>
                        <td>{flight.originCountry}</td>
                        <td>{flight.icao24}</td>
                        <td>{flight.last_contact}</td>
                        <td>{flight.velocity}</td>
                      </tr>
                      )}
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




export default Dashboard;

/*
mymap = L.map('worldMap').setView([40, 60], 3)


L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 570,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiYWlyY29udHJvbCIsImEiOiJja245ZWZpcXMwZGY3MnBtdWkycnl2ZXEwIn0.0ghCR75wQQC2lQ7aeF1Kow'
}).addTo(mymap);

const addPlane = (plane) => {
  L.marker([plane.latitude, plane.longitude]).addTo(mymap)
}*/