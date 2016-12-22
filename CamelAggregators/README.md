# CamelAggregators

This module shows a very particular use case that we came across where we had hundreds of thousands of messages that were non unique, based on an ID field.
Instead of throwing all of these messages into the queue, we tried to deduplicate these messages by grouping them together in an aggregator before sending
them off to a queue of sorts.
In tests, this component is seda, but if you have activemq running locally, you can change it back to the default of activemq component.


SpawnObjectsRoute - > Create a set number of objects and send them down the camel route
 - object.id.minValue = Minimum id number
 - object.id.maxValue = Maximum id number
 - global.howManyObjects = How many DemoObjects to spawn

ObjectsAggregatorRoute - > Waits a set amount of time looking for objects of the same id come in. Can auto-complete at another set value of aggregations
 - global.waitTime = How long will the aggregator wait before sending off exchange to queue
 - global.maxAggregations = If we see this many values aggregated, immediately move forward with sending to the queue

CollectorToLogRoute - > Pull from the queue and dump into the log


ObjectAggregatorTest - > Run this test to see log statements to console for
 - Sending: {object.toString()}
 - Receiving: {object.toString()}

> If you have activemq running locally, point the configuration
> found in /main/resources/activemq-context.xml to your broker URL.