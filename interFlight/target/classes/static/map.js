//CREATE MAP

var mymap = L.map('worldMap').setView([-10, 15], 2);


L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 570,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiYWlyY29udHJvbCIsImEiOiJja245ZWZpcXMwZGY3MnBtdWkycnl2ZXEwIn0.0ghCR75wQQC2lQ7aeF1Kow'
}).addTo(mymap);


const addPlane = (plane) => {
    L.marker([plane.latitude, plane.longitude]).addTo(mymap)
}




/*
<MapContainer center={[51.505, -0.09]} zoom={3} scrollWheelZoom={true}>
<TileLayer
  attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
/>
</MapContainer>*/


