
<div align="center">

<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/cover.png?raw=true" alt="drawing" width="200"/>

<h2> play codex online</h2>


play on the terminal or in gui.
todo: gif
<img src="" alt="da cambiare" style="width: 500px;" >
</div>

## 🗺️ map 
- [<code>🌐 Team</code>](#-team)
- [<code>⚙️ Project Specification </code>](#%EF%B8%8F-project-specification)
- [<code>🚦️ Implemented Functionalities</code>](#%EF%B8%8F-implemented-functionalities)
- [<code>📝 Test Cases</code>](#-test-cases)
- [<code>📦 Installation</code>](#-installation)
- [<code>🕹 Usage</code>](#-usage)

# Codex Naturalis Board Game - Software Engineering Project

**Teacher**: Gianpaolo Cugola

## 🌐 Team
* [Andrea Tarabotto](https://github.com/andrea-809)
* [Francesco Zuliani](https://github.com/francescomartino2002)
* [Nicola Tummolo](https://github.com/NTum07)
* [Gabriel Voss](https://github.com/voss01)

## ⚙️ Project Specification

The project consists of a Java version of the board game *Codex Naturalis*, made by Cranio Creations. You can find the real game [here](https://www.craniocreations.it/prodotto/codex-naturalis).

Project requirements: [link](https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/requirements.pdf?raw=true).

The final version includes:
* High level UML diagram.
* UML diagram generated from the code by automated tools.
* Working game implementation, which has to be rules compliant.
* Source code of the implementation.
* Source code of unity tests.

## 🚦️ Implemented Functionalities

### Main functionalities
| Functionality                    | Status |
|:---------------------------------|:------:|
| Basic rules                      |   ✅    |
| Complete rules                   |   ✅    |
| Socket and RMI                           |   ✅    |
| CLI _(Command Line Interface)_   |   ✅    |
| GUI _(Graphical User Interface)_ |   ✅    |

### Advanced functionalities
| Functionality                | Status |
|:-----------------------------|:------:|
| Group Chat and Direct messages         |   ✅      |
| Simultaneous games           |   ✅    |
| Persistence                  | ⛔      |
| Resilience to disconnections |   ✅     |

⛔ Not implemented &nbsp;&nbsp;&nbsp;&nbsp; ✅ Implemented

## 📝 Test Cases
| Package    | Class, %     | Method, %     | Line, %       |
|:-----------|:-------------|:--------------|:--------------|
| Model      | 96% (48/50) | 67% (196/292) | 76% (780/1024) |
| Controller | 100% (9/9)   | 59% (44/45)   | 66% (223/257) |

<p align="center">⚠️ disclaimer </p>

> We have also tested the controller by excluding the lines that are not essential for its purpose. These lines interface with the network structure and communicate with the TUI. As a result, we achieve around 80% coverage, even for the controller.
## 📦 Installation

### Requirements

Regardless of the operating system, you must have installed the following programs:
- Java 21
- Maven


### Instructions
1. Clone this repository:
    ```shell
   git clone https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss
   ```


2. execute the server and a client:
    ```shell
    java -jar server.jar
    java -jar client.jar
    ```
## 🕹 Usage
<div class="table-devenvironment">
  <table style="font-size: 11px">
  <tr>
  <td valign="top" width="50%">
  
  #### 🖥️ GUI
  
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/GUI_gameBoard.png?raw=true" width="260" align="right" />
  
  </td>
  <td valign="top" width="50%">
  
  #### ⌨️ TUI
  
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/TUI_gameBoard.png?raw=true" width="260" align="right" />
  



  </td>
  </tr>
  </table>
</div>

### LOGIN
<div class="table-devenvironment">
  <table style="font-size: 11px">
  <tr>
  <td valign="top" width="50%">
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/Welcome.png?raw=true" width="260" align="right" />
  
  </td>
  <td valign="top" width="50%"> 
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/TUI_gameBoard.png?raw=true" width="260" align="right" />
  



  </td>
  </tr>
  </table>
</div>


> The user can choose a username, if it already exists and it's playing the user will be notified by a message.

### LOBBY
<div class="table-devenvironment">
  <table style="font-size: 11px">
  <tr>
  <td valign="top" width="50%">
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/lobby.png?raw=true" width="260" align="right" />
  
  </td>
  <td valign="top" width="50%"> 
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/TUI_gameBoard.png?raw=true" width="260" align="right" />
  



  </td>
  </tr>
  </table>
</div>

> In the lobby players can choose weather to join or create a team.

### ROOM CREATION
<div class="table-devenvironment">
  <table style="font-size: 11px">
  <tr>
  <td valign="top" width="50%">
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/game_creation.png?raw=true" width="260" align="right" />
  
  </td>
  <td valign="top" width="50%"> 
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/TUI_gameBoard.png?raw=true" width="260" align="right" />
  



  </td>
  </tr>
  </table>
</div>


> the user can from this section choose the name of the team and how many people should be there. 
