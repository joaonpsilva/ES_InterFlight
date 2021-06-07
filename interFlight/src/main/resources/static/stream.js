//STREAM
class Stream{
    constructor(endpoint){
        console.log(endpoint)

        this.endpoint = endpoint
        this.source = null
    }

    init(){
        this.source = new EventSource(this.endpoint)
        
        this.source.addEventListener('message', handleEvent)

        this.source.onerror = () => {
            console.log("ERROR")
            this.source.close()
        }
    }

    end(){
        this.source.close()
    }
}
const handleEvent = (event) => {
    const flights = JSON.parse(event.data)
    console.log("new events")

    if (stream.endpoint != "/realtime/getAllPlanes"){
        updateMap(flights.flightdata)
    }
    updateTable(flights.flightdata)
}


//rows in table
function updateTable(data){
    
    var old_tbody = document.getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');

    data.forEach(flight =>{

        let row = new_tbody.insertRow();

        let cell;
        cell = row.insertCell(0);
        cell.innerHTML = flight.originCountry;
        cell = row.insertCell(1);
        cell.innerHTML = flight.icao24;
        cell = row.insertCell(2);
        cell.innerHTML = flight.last_contact;
        cell = row.insertCell(3);
        cell.innerHTML = flight.longitude;
        cell = row.insertCell(4);
        cell.innerHTML = flight.latitude;
        cell = row.insertCell(5);
        cell.innerHTML = flight.velocity;

    })
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody) //replacebodies
}

//points in map 
var layerGroup = L.layerGroup().addTo(mymap);
function updateMap(data){
    layerGroup.clearLayers();
    layerGroup = L.layerGroup().addTo(mymap);

    data.forEach(flight =>{
        L.marker([flight.latitude, flight.longitude]).addTo(layerGroup);
    })
}


//Init Stream
var stream = new Stream("/realtime/getAllPlanes")
window.onload = () =>{
    console.log("stream initiated")
    stream.init()
}

window.onbeforeunload = () => {
    stream.end()
}


function connect(url){

    newurl = url + "?value=" + document.getElementById(url).value    
    stream.end()
    stream = new Stream(newurl)
    stream.init()
}


