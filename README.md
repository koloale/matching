#Matching Engine
=====================

Matching Engine is an application for executing client orders for predefined instruments ("A", "B", "C" и "D") on exchange.

##Build
-----
    git clone https://github.com/koloale/matching.git
    cd matching
    mvn package


##Usage
-----

Run Matching Engine with Java 1.8:

    java -jar target/matching-1.0-SNAPSHOT.jar data/clients.txt data/orders.txt result.txt


After matching orders the application creates output file in result.txt containing clients balanc of all instruments after trading.
