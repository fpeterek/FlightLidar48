let map
let markerLayer
let mouse

let selected = {
    aircraft: null,
    flight: null
}
let flights = []

function adjustLat(lat) {
    if (lat < -90) {
        return lat + 180
    } else if (lat > 90) {
        return lat - 180
    }
    return lat
}

function adjustLon(lon) {
    if (lon < -180) {
        return lon + 360
    } else if (lon > 180) {
        return lon - 360
    }
    return lon
}

class Flight {
    constructor(jsonObject) {
        this.number = jsonObject.number
        this.departure = jsonObject.departure
        this.origin = jsonObject.origin
        this.destination = jsonObject.destination
        this.aircraft = jsonObject.aircraft
        this.lat = jsonObject.lat
        this.lon = adjustLon(jsonObject.lon)
        this.squawk = jsonObject.squawk
        this.altitude = jsonObject.altitude
        this.direction = jsonObject.direction
        this.speed = jsonObject.speed
    }

    estimatePosition(dt) {
        let mps = (this.speed * 1.852) / 3.6
        let dir = this.direction * (Math.PI/180)
        let dx = mps * dt * Math.cos(dir)
        let dy = mps * dt * Math.sin(dir)

        let deltaLat = dy / 6378000 * 180 * Math.PI
        let newLat = adjustLat(this.lat + deltaLat)

        let deltaLon = (dx / 6378000 * 180 * Math.PI) / Math.cos(newLat * (Math.PI/180))
        let newLon = adjustLon(this.lon + deltaLon)

        this.lat = newLat
        this.lon = newLon
    }

    sprite() {
        let dist = this.direction % 15
        dist = (dist <= 7) ? (-dist) : (15-dist)

        let sprite = (this.direction + dist) % 360
        let color = (selected.flight != null && this.number === selected.flight.number) ? 's' : ''
        return 'sprites/' + sprite + color + '.png'
    }

    asMarker() {

        let marker = JAK.mel("div")
        let img = JAK.mel("img", {src: this.sprite()})
        marker.appendChild(img)

        let text = JAK.mel(
            "div",
            {},
            { position: "absolute", left: "-14px", top: "35px", textAlign: "center", color: "white", fontWeight: "bold",
              width: "60px", backgroundColor: "black", fontSize: "13px" }
        )
        text.innerHTML = this.aircraft
        marker.appendChild(text)

        return new SMap.Marker(
            SMap.Coords.fromWGS84(this.lon, this.lat),
            this.number,
            {url: marker}
        )
    }

    mach() {
        return this.speed * 0.001511784
    }

}

function estimate(dt) {
    for (let fl of flights) {
        fl.estimatePosition(dt)
    }
}

function render() {
    markerLayer.removeAll()
    for (let fl of flights) {
        markerLayer.addMarker(fl.asMarker())
    }
    markerLayer.enable()
}

function parseResults(results) {
    let lst = []
    for (let res of results) {
        let flight = new Flight(res)
        lst.push(flight)
    }
    return lst
}

function updateSelected(tracked) {
    if (tracked == null || tracked.flight == null || tracked.aircraft == null) {
        return
    }
    selected.flight = new Flight(tracked.flight)
    selected.aircraft = tracked.aircraft

    if (flights.length === 0) {
        flights = [selected.flight]
    }

    formatFlight()
}

function handleResponse(xhr) {
    if (xhr.status !== 200) {
        estimate(1.0)
        console.log("Server responded with " + xhr.status)
        return
    }
    let resp = xhr.responseText

    let parsed = JSON.parse(resp)

    if (parsed.status === 'failure') {
        console.log(parsed.reason)
        return estimate(1.0)
    }

    flights = parseResults(parsed.results)
    updateSelected(parsed.tracked)
}

function getEncodedUrl(url) {
    let vp = map.getViewport()
    let lbx = map.getZoom() > 3 ? vp.lbx : -180.0
    let lby = vp.lby
    let rtx = map.getZoom() > 3 ? vp.rtx : 180.0
    let rty = vp.rty
    let encoded = url + "?lbx=" + lbx + "&lby=" + lby + "&rtx=" + rtx + "&rty=" + rty

    if (selected.flight != null) {
        encoded = encoded + "&tracked=" + encodeURIComponent(selected.flight.number)
    }

    return encoded
}

function createRequest() {
    let xhr = new XMLHttpRequest()
    xhr.open("GET", getEncodedUrl("http://127.0.0.1:5050/get"), true)
    xhr.timeout = 500
    return xhr
}

function createSearchRequest(term) {
    let xhr = new XMLHttpRequest()
    xhr.open("GET", "http://127.0.0.1:5050/search?term=" + encodeURIComponent(term), true)
    xhr.timeout = 500
    return xhr
}

function createPair(row, label, text) {

    let td1 = document.createElement('td')
    td1.innerText = label
    let td2 = document.createElement('td')
    td2.innerText = text

    row.appendChild(td1)
    row.appendChild(td2)
}

function formatFlight() {
    let table = document.createElement('table')
    table.id = 'outTable'

    let r1 = document.createElement('tr')
    createPair(r1, 'Flight', selected.flight.number)
    createPair(r1, 'Departure', selected.flight.departure)
    createPair(r1, 'Origin', selected.flight.origin)
    createPair(r1, 'Destination', selected.flight.destination)
    table.appendChild(r1)

    let r2 = document.createElement('tr')
    createPair(r2, 'Latitude', Math.round(selected.flight.lat * 1000) / 1000)
    createPair(r2, 'Longitude', Math.round(selected.flight.lon * 1000) / 1000)
    createPair(r2, 'Squawk', selected.flight.squawk)
    createPair(r2, 'Altitude', selected.flight.altitude + ' ft')
    table.appendChild(r2)

    let r3 = document.createElement('tr')
    createPair(r3, 'Direction', selected.flight.direction)
    createPair(r3, 'Speed', selected.flight.speed + ' kts')
    createPair(r3, 'Mach', Math.round(selected.flight.mach() * 100) / 100)
    createPair(r3, 'Aircraft', selected.aircraft.registration)
    table.appendChild(r3)

    let r4 = document.createElement('tr')
    createPair(r4, 'MSN', selected.aircraft.msn)
    createPair(r4, 'Airline', selected.aircraft.airline)
    createPair(r4, 'Type', selected.aircraft.type)
    createPair(r4, 'Age', selected.aircraft.age + ((selected.aircraft.age > 1) ? ' years' : ' year'))
    table.appendChild(r4)

    document.getElementById('out').innerHTML = ''
    document.getElementById('out').appendChild(table)
}

function formatAirport(airport) {

    let table = document.createElement('table')
    table.id = 'outTable'

    let createRow = function(label, text) {
        let tr = document.createElement('tr')
        createPair(tr, label, text)
        return tr
    }

    table.appendChild(createRow('IATA', airport.iata))
    table.appendChild(createRow('ICAO', airport.icao))
    table.appendChild(createRow('Name', airport.name))

    document.getElementById('out').innerHTML = ''
    document.getElementById('out').appendChild(table)
}

function handleSearchResponse(xhr) {
    if (xhr.status !== 200) {
        console.log("Server responded with " + xhr.status)
        return
    }
    let resp = xhr.responseText

    let parsed = JSON.parse(resp)

    if (parsed.status === 'failure') {
        console.log(parsed.reason)
        return
    }

    console.log("Search response received")

    if (parsed.tracked.airport != null) {
        selected.flight = null
        selected.aircraft = null
        formatAirport(parsed.tracked.airport)
    }

    if (parsed.tracked.flight != null) {
        updateSelected(parsed.tracked)
        map.setCenter(SMap.Coords.fromWGS84(selected.flight.lon, selected.flight.lat))
    }
}

function performSearch() {
    console.log("Performing search")
    let term = document.getElementById("sbox").value
    let xhr = createSearchRequest(term)
    xhr.onload = function() {
        handleSearchResponse(xhr)
    }
    xhr.ontimeout = function(e) {
        console.log('Not found')
    }
    xhr.onerror = function(e) {
        console.log('Not found')
    }
    xhr.send()
}

function update() {
    let xhr = createRequest()
    xhr.onload = function() {
        handleResponse(xhr)
        render()
    }
    xhr.ontimeout = function(e) {
        console.log('Timeout while calling API')
        estimate(1.0)
        render()
    }
    xhr.onerror = function(e) {
        console.log('Failed to make call to server')
        estimate(1.0)
        render()
    }
    xhr.send()
}

function initMap() {
    let center = SMap.Coords.fromWGS84(18.16, 49.83);

    map = new SMap(JAK.gel("mapa"), center, 8);
    map.addDefaultLayer(SMap.DEF_BASE).enable();
    map.addDefaultControls();
    markerLayer = new SMap.Layer.Marker()
    map.addLayer(markerLayer)
    markerLayer.enable()

    let sync = new SMap.Control.Sync({bottomSpace:100})
    map.addControl(sync)

    mouse = new SMap.Control.Mouse(SMap.MOUSE_PAN | SMap.MOUSE_WHEEL | SMap.MOUSE_ZOOM)
    map.addControl(mouse)

    map.getSignals().addListener(this, "marker-click", function(e) {
        let marker = e.target
        let flight = marker.getId()

        for (let fl of flights) {
            if (fl.number === flight) {
                selected.flight = fl
                render()
            }
        }
    })

    setInterval(update, 1000)
}
