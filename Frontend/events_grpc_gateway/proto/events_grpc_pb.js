// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('@grpc/grpc-js');
var proto_events_pb = require('./events_pb.js');

function serialize_EmptyRequest(arg) {
  if (!(arg instanceof proto_events_pb.EmptyRequest)) {
    throw new Error('Expected argument of type EmptyRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EmptyRequest(buffer_arg) {
  return proto_events_pb.EmptyRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_EmptyResponse(arg) {
  if (!(arg instanceof proto_events_pb.EmptyResponse)) {
    throw new Error('Expected argument of type EmptyResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EmptyResponse(buffer_arg) {
  return proto_events_pb.EmptyResponse.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_EventIdRequest(arg) {
  if (!(arg instanceof proto_events_pb.EventIdRequest)) {
    throw new Error('Expected argument of type EventIdRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EventIdRequest(buffer_arg) {
  return proto_events_pb.EventIdRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_EventRequest(arg) {
  if (!(arg instanceof proto_events_pb.EventRequest)) {
    throw new Error('Expected argument of type EventRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EventRequest(buffer_arg) {
  return proto_events_pb.EventRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_EventResponse(arg) {
  if (!(arg instanceof proto_events_pb.EventResponse)) {
    throw new Error('Expected argument of type EventResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EventResponse(buffer_arg) {
  return proto_events_pb.EventResponse.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_EventsResponse(arg) {
  if (!(arg instanceof proto_events_pb.EventsResponse)) {
    throw new Error('Expected argument of type EventsResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_EventsResponse(buffer_arg) {
  return proto_events_pb.EventsResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


var EventServiceService = exports.EventServiceService = {
  getAllEvents: {
    path: '/EventService/GetAllEvents',
    requestStream: false,
    responseStream: true,
    requestType: proto_events_pb.EmptyRequest,
    responseType: proto_events_pb.EventsResponse,
    requestSerialize: serialize_EmptyRequest,
    requestDeserialize: deserialize_EmptyRequest,
    responseSerialize: serialize_EventsResponse,
    responseDeserialize: deserialize_EventsResponse,
  },
  getEventById: {
    path: '/EventService/GetEventById',
    requestStream: false,
    responseStream: false,
    requestType: proto_events_pb.EventIdRequest,
    responseType: proto_events_pb.EventResponse,
    requestSerialize: serialize_EventIdRequest,
    requestDeserialize: deserialize_EventIdRequest,
    responseSerialize: serialize_EventResponse,
    responseDeserialize: deserialize_EventResponse,
  },
  addEvent: {
    path: '/EventService/AddEvent',
    requestStream: false,
    responseStream: false,
    requestType: proto_events_pb.EventRequest,
    responseType: proto_events_pb.EventResponse,
    requestSerialize: serialize_EventRequest,
    requestDeserialize: deserialize_EventRequest,
    responseSerialize: serialize_EventResponse,
    responseDeserialize: deserialize_EventResponse,
  },
  updateEvent: {
    path: '/EventService/UpdateEvent',
    requestStream: false,
    responseStream: false,
    requestType: proto_events_pb.EventRequest,
    responseType: proto_events_pb.EventResponse,
    requestSerialize: serialize_EventRequest,
    requestDeserialize: deserialize_EventRequest,
    responseSerialize: serialize_EventResponse,
    responseDeserialize: deserialize_EventResponse,
  },
  deleteEventById: {
    path: '/EventService/DeleteEventById',
    requestStream: false,
    responseStream: false,
    requestType: proto_events_pb.EventIdRequest,
    responseType: proto_events_pb.EmptyResponse,
    requestSerialize: serialize_EventIdRequest,
    requestDeserialize: deserialize_EventIdRequest,
    responseSerialize: serialize_EmptyResponse,
    responseDeserialize: deserialize_EmptyResponse,
  },
};

exports.EventServiceClient = grpc.makeGenericClientConstructor(EventServiceService);
