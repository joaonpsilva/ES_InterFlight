var stream = null;

class Stream{

    constructor(endpoint, component){
        this.component = component
        this.endpoint = "http://localhost:8080" + endpoint
        console.log(endpoint)
        this.source = null
    }

    init(){
        console.log("Initating connection")
        this.source = new EventSource(this.endpoint)
        
        this.source.addEventListener('message', this.handleEvent)

        this.source.onerror = () => {
            console.log("ERROR")
            this.source.close()
        }
    }

    end(){
        this.source.close()
    }


    handleEvent = (event) => {
        const flights = JSON.parse(event.data)
        
        this.updateflights(flights.flightdata)
    }

    updateflights(data){
        console.log(data)

        try{
            this.component.setFlights(data)
        } catch (error) {}

    }
}



function connect(url, component){
    if (stream != null)
        stream.end()
    stream = new Stream(url, component)
    stream.init()

}

export default connect