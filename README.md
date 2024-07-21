# Fuel-Level-Alert-System

Note : Download Latest Kafka setup and add environment variable of "kafka/bin/windows" and "kafka/bin".

1.To run the project, execute these two commands before starting the project:

	Note :  1.Open cmd at kafka/bin/windows location to run zookeeper and kafka server
		2.Run both cammands in 2 different cmd

	1. Start Zookeeper server
		->zookeeper-server-start.bat ..\..\config\zookeeper.properties

	2. Start Kafka server
		->kafka-server-start.bat ..\..\config\server.properties

2. Run Producer Service project

3. Run Consumer Service project
