let map;
let markerLayer;
let selected = null
let aircraft = [];

function adjustLat(lat) {
    if (lat < -90) {
        return lat + 180
    } else if (lat > 90) {
        return lat - 180
    }
    return lat
}

function adjustLon(lon) {
    if (lon > 360) {
        return lon - 360
    } else if (lon < 0) {
        return lon + 360
    }
    return lon
}

class Aircraft {
    constructor(jsonObject) {
        this.number = jsonObject.number
        this.departure = jsonObject.departure
        this.origin = jsonObject.origin
        this.departure = jsonObject.destination
        this.aircraft = jsonObject.aircraft
        this.lat = jsonObject.lat
        this.lon = jsonObject.lon
        this.squawk = jsonObject.squawk
        this.altitude = jsonObject.altitude
        this.direction = jsonObject.direction
        this.speed = jsonObject.speed
    }

    estimatePosition(dt) {
        let kmh = (this.speed * 1.852) / 3.6
        let dir = this.direction * (Math.PI/180)
        let dx = kmh * dt * Math.cos(dir)
        let dy = kmh * dt * Math.sin(dir)

        let deltaLat = dy / 6378000 * 180 * Math.PI
        let newLat = this.lat + deltaLat

        let deltaLon = (dx / 6378000 * 180 * Math.PI) / Math.cos(newLat * (Math.PI/180))
        let newLon = this.lon + deltaLon

        this.lat = adjustLat(newLat)
        this.lon = adjustLon(newLon)
    }

    sprite() {
        let dist = this.direction % 15
        if (dist <= 7) {
            dist = -dist
        } else {
            dist = 15-dist
        }
        let sprite = (this.direction + dist) % 360
        return 'sprites/' + sprite + '.png'
    }

    asMarker() {
        return new SMap.Marker(
            SMap.Coords.fromWGS84(this.lon, this.lat),
            this.aircraft,
            {url: this.sprite()}
        )
    }

}

function estimate(dt) {
    for (let ac of aircraft) {
        ac.estimatePosition(dt)
    }
}

function render() {
    markerLayer.removeAll()
    for (let ac of aircraft) {
        markerLayer.addMarker(ac.asMarker())
    }
    markerLayer.enable()
}

function toAircraftList(results) {
    let lst = []
    let selectedUpdate = false
    for (let res of results) {
        let ac = new Aircraft(res)
        lst.push(ac)
        if (selected != null && ac.aircraft === selected.aircraft) {
            selected = ac
            selectedUpdate = true
        }
    }
    if (!selectedUpdate) {
        selected = false
    }
    return lst
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

    aircraft = toAircraftList(parsed.results)
}

function getEncodedUrl(url) {
    let vp = map.getViewport()
    let lbx = vp.lbx
    let lby = vp.lby
    let rtx = vp.rtx
    let rty = vp.rty
    return url + "?lbx=" + lbx + "&lby=" + lby + "&rtx=" + rtx + "&rty=" + rty
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

    selected = new Aircraft(parsed.result)
    map.setCenter(SMap.Coords.fromWGS84(selected.lat, selected.lon))
}

function performSearch() {
    let term = document.getElementById("sbox").innerText
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

    let sync = new SMap.Control.Sync({bottomSpace:40})
    map.addControl(sync)

    setInterval(update, 1000)
}
