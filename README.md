# Masters of Renaissance
![Masters-of-Renaissance_box3D_ombra](https://user-images.githubusercontent.com/69718409/123828764-1c20b680-d902-11eb-893a-d897be8df36a.png)


In Masters of Renaissance, you are an important citizen of Florence and your goal is to increase your fame and prestige. Take resources from the market and use them to buy new cards. Expand your power both in the city and in the surrounding territories! Every card gives you a production power that transforms the resources so you can store them in your strongbox. Try to use the leaders’ abilities to your advantage and don’t forget to show your devotion to the Pope!

Masters of Renaissance is a game with simple rules offering deep strategic choices of action selection and engine building.

## Authors

| Name        | Username    | Email         |
| :---        |    :----:   |          ---: |
| Nemanja Antonic      | @nema-oss       | nemanja.antonic@mail.polimi.it   |
| Renè Bwanika   | @vanika        | rene.bwanika@mail.polimi.it      |
| Chiara Buonagurio   | @ChiaraBuonagurio        | chiara.buonagurio@mail.polimi.it      |

## Functionalities

|     |     |
| --- | --- |
| **Functionality** | **State** |
| Complete rules | GREEN |
| CLI | GREEN |
| GUI | GREEN |
| Multiple games | GREEN |
| Local game | GREEN |
| Disconnection resilience | GREEN |
| Socket | GREEN |


## Instructions for build and execution:
The jar is the same for both server and client. You can build it under your preferred OS and launch it following the instructions below. The project has been developed using JAVA 15 and JavaFX 16. 
The CLI has been tested for Ubuntu 20.04.

### Build instructions:
The jar is built using "Maven Shade Plugin": inside the IntelliJ IDE, clone the repository and launch "install" under "lifecycle" section in the Maven toolbar. The generated jar will be placed in the "shade" folder of your project.

If you want to use Maven in the terminal, you can execute:
```bash 
mvn install
```
In the project's root folder (same folder of "pom.xml"). The generated jar will be placed in the "shade" folder of your project.

### Execution instructions:
#### Server
In terminal execute:
```bash
java -jar AM10.jar -server 
```
The server will be listening on the default port 1234.

If you would like to change the port:
```bash
java -jar AM10.jar -server PORT
```
Where you can replace PORT with your preferred port number. Example:
```bash 
java -jar AM10.jar -server 8080
```

#### Client
To launch the game on the command line interface (CLI) make sure your terminal is in fullscreen mode, then execute:
```bash
java -jar AM10.jar -client -cli
```
To launch the program with the graphical user interface (GUI), double-click on the jar (on Ubuntu 20.04 first make sure to mark as executable the jar file). You can also execute in the terminal:
```bash
java -jar AM10.jar -client -gui
```

To launch the program in local mode execute in the terminal.

```bash
java -jar AM10.jar -client -local -gui
```

