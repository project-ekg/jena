import {Feature, Map, Overlay, View} from 'ol/index.js';
import {OSM, Vector as VectorSource} from 'ol/source.js';
import {Point} from 'ol/geom.js';
import {Tile as TileLayer, Vector as VectorLayer} from 'ol/layer.js';
import {useGeographic} from 'ol/proj.js';

useGeographic();

//Read Coordinates into array
var coordinates = []
var resp = await fetch('./data/coordinates.json')
var json = await resp.json()
for (var ele of json) {
  coordinates.push([ele.lon, ele.lat])
}

//Query
var submitQuery = document.getElementById("submit")
submitQuery.addEventListener("click", async () => {
    var _query = document.getElementById("query").value
    console.log(_query)
    var resp = await fetch('http://localhost:3000/add_query/', {
        method: "POST",
        mode: "no-cors",
        body: _query,
    });
    console.log(resp)
})

var features = []
for (var coordinate of coordinates) {
  features.push(new Feature(new Point(coordinate)))
};


const map = new Map({
  target: 'map',
  view: new View({
    center: [0,0],
    zoom: 0,
  }),
  layers: [
    new TileLayer({
      source: new OSM(),
    }),
    new VectorLayer({
      source: new VectorSource({
        features: features
      }),
      style: {
        'circle-radius': 5,
        'circle-fill-color': 'red',
      },
    }),
  ],
});


function formatCoordinate(coordinate) {
  return `
    <table>
      <tbody>
        <tr><th>lon</th><td>${coordinate[0].toFixed(5)}</td></tr>
        <tr><th>lat</th><td>${coordinate[1].toFixed(5)}</td></tr>
      </tbody>
    </table>`;
}

const info = document.getElementById('info');
map.on('moveend', function () {
    const view = map.getView();
    const center = view.getCenter();
    info.innerHTML = formatCoordinate(center);
});

