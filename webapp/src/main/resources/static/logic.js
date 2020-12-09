let map;
let markerLayer;

function redraw(xhr) {
    if (xhr.status != 200) {
        console.log("Server responded with " + xhr.status)
    }
    let resp = xhr.responseText

    parsed = JSON.parse(resp)
    markerLayer.removeAll()
    console.log(parsed)
    for (let index in parsed) {
        let aircraft = parsed[index]
        console.log(aircraft)
        markerLayer.addMarker(new SMap.Marker(SMap.Coords.fromWGS84(aircraft.lon, aircraft.lat), aircraft.aircraft, {url: '32.png'}))
    }
    markerLayer.enable()

    document.getElementById("out").innerText = xhr.responseText
}

function getEncodedUrl(url) {
    let vp = map.getViewport()
    let lbx = vp.lbx
    let lby = vp.lby
    let rtx = vp.rtx
    let rty = vp.rty
    return url + "?lbx=" + lbx + "&lby=" + lby + "&rtx=" + rtx + "&rty=" + rty
}

function sendRequest() {
    let xhr = new XMLHttpRequest()
    xhr.open("GET", getEncodedUrl("http://127.0.0.1:5050/get"), true)
    xhr.send()
    return xhr
}

function update() {
    let xhr = sendRequest()
    xhr.onload = function() { redraw(xhr) }
}

function initMap() {
    let center = SMap.Coords.fromWGS84(18.16, 49.83);

    map = new SMap(JAK.gel("mapa"), center, 8);
    map.addDefaultLayer(SMap.DEF_BASE).enable();
    map.addDefaultControls();
    markerLayer = new SMap.Layer.Marker()
    map.addLayer(markerLayer)
    markerLayer.enable()

    setInterval(update, 1000)
}
