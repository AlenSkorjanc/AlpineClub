const grpc = require("@grpc/grpc-js");
var events = require('./proto/events_pb');
var services = require('./proto/events_grpc_pb');
const fs = require('fs');
const path = require('path');
const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const serverCert = fs.readFileSync(path.resolve(__dirname, 'gateway.pem'));
var client = new services.EventServiceClient('localhost:8444', grpc.credentials.createSsl(serverCert));

function eventObjToJson(eventObj) {
    return {
        id: eventObj.getId(),
        userId: eventObj.getUserId(),
        name: eventObj.getName(),
        description: eventObj.getDescription(),
        start: eventObj.getStart(),
        end: eventObj.getEnd(),
    };
}

// Get all events
app.get('/events', (req, res) => {
    const call = client.getAllEvents(new events.EmptyRequest());
    const eventsList = [];

    call.on('data', (data) => {
        data.getEventsList().forEach((eventObj) => {
            eventsList.push(eventObjToJson(eventObj));
        });
    });

    call.on('end', () => {
        res.json(eventsList);
    });
});

// Get event by ID
app.get('/events/:id', (req, res) => {
    const id = req.params.id;

    let request = new events.EventIdRequest();
    request.setId(id);

    client.getEventById(request, (err, response) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        }

        res.json(eventObjToJson(response.getEvent()));
    });
});

// Add event
app.post('/events', (req, res) => {
    const event = req.body;

    let eventObj = new events.Event();
    eventObj.setUserId(event.userId);
    eventObj.setName(event.name);
    eventObj.setDescription(event.description);
    eventObj.setStart(event.start);
    eventObj.setEnd(event.end);

    let request = new events.EventRequest();
    request.setEvent(eventObj);

    client.addEvent(request, (err, response) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        }

        res.json(eventObjToJson(response.getEvent()));
    });
});

// Update event
app.put('/events/:id', (req, res) => {
    const id = req.params.id;
    const event = req.body;

    let eventObj = new events.Event();
    eventObj.setId(id);
    eventObj.setUserId(event.userId);
    eventObj.setName(event.name);
    eventObj.setDescription(event.description);
    eventObj.setStart(event.start);
    eventObj.setEnd(event.end);

    let request = new events.EventRequest();
    request.setEvent(eventObj);

    client.updateEvent(request, (err, response) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        }

        res.json(eventObjToJson(response.getEvent()));
    });
});

// Delete event by ID
app.delete('/events/:id', (req, res) => {
    const id = req.params.id;

    let request = new events.EventIdRequest();
    request.setId(id);

    client.deleteEventById(request, (err, _) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        }

        res.json({ message: "Successfully deleted event" });
    });
});

// Start the server
const port = 3300; // Change to desired port number
app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});