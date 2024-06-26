
<div align="center">

![codex naturalis](https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/cover.png?raw=true)

<h2> play codex online</h2>


play on the terminal or in gui.
todo: gif
<img src="" alt="da cambiare" style="width: 500px;" >
</div>

## 🗺️ map 
- [<code>🌐 Team</code>](#-team)
- [<code>⚙️ Project Specification </code>](#%EF%B8%8F-project-specification)
- [<code>🚦️ Implemented Functionalities</code>](#%EF%B8%8F-implemented-functionalities)
- [<code>📝 Test Cases</code>](#%EF%B8%8F-test-cases)
- [<code>📦 Installation</code>](#-installation)
- [<code>🕹 Usage</code>](#-usage)

# Codex Naturalis Board Game - Software Engineering Project

<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/cover.png?raw=true" width="260" align="right" />

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

## 📝 Test cases
| Package    | Class, %     | Method, %     | Line, %       |
|:-----------|:-------------|:--------------|:--------------|
| Model      | 87% (29/29) | 87% (183/208) | 87% (597/721) |
| Controller | 87% (5/5)   | 87% (44/45)   | 87% (223/257) |

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
  
  #### TUI
  
<img src="https://github.com/Ingegneria-del-software-polimi-2024/ing-sw-2024-zuliani-tarabotto-tummolo-voss/blob/main/screenshots/TUI_gameBoard.png?raw=true" width="260" align="right" />
  




  
  </td>
  </tr>
  </table>
</div>

