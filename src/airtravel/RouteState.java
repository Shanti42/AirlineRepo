package airtravel;

import java.time.LocalTime;
import java.util.*;

/**
 * Keeps track of the state of the route
 */
final class RouteState {

    private Map<Airport, RouteNode> airportNode;

    private final NavigableSet<RouteNode> unreached;


    private RouteState(Set<Airport> airports, Airport origin, LocalTime departureTime){
        unreached = new TreeSet<>();
        airportNode = new HashMap<>();
        airportNode.put(origin, RouteNode.of(origin, new RouteTime(departureTime.plus(origin.getConnectionTimeMin())), null));
        RouteNode tempNode;
        for(Airport airport: airports){
            tempNode = RouteNode.of(airport);
            airportNode.put(airport, tempNode);
            unreached.add(tempNode);
        }
    }

    //replaces the route node for the corresponding airport, assumes airport is in the route state and is unreached
    final void replaceNode(RouteNode routeNode){
        Objects.requireNonNull(routeNode, "RouteState, replaceNode -> given route node is null");
        RouteNode prevNode = airportNode.replace(routeNode.getAirport(), routeNode);
        unreached.remove(prevNode);
        unreached.add(routeNode);
    }

    //returns true if all airports are reached
    final boolean allReached(){
        return unreached.isEmpty();
    }

    final RouteNode closestUnreached(){
        RouteNode smallestArivalTime = unreached.pollFirst();
        if(smallestArivalTime != null){
            return smallestArivalTime;
        } else {
            throw new NoSuchElementException("All Airports have been reached");
        }
    }

    //returns the route node corresponding to the airport, assumes the airport is in the route state
    final RouteNode airportNode(Airport airport){
        Objects.requireNonNull(airport, "RouteState, airportNode -> airport is null");
        return airportNode.get(airport);
    }

}
