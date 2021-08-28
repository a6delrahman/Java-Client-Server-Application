# Java-Client-Server-Application

The aim of this documentation is to illustrate the concepts of Java multithreading and Java networking (socket programming) that were used to implement
this client / server application.

Use Case:
To programm a java application that resembels th "Who wants to be a millionaire!" game. Several players should compete against each other in a quiz. The game should be a pure command line application without a graphical user interface.

Scope:
The entire Unicode character set can be used.
• All output appears on the command line on both the clients and the server.\n
• Use of Java Sockets for communication between the clients and the server.
• The server reads the text file with the quiz questions. A quiz question consists of one question, each with four possible answers A to C (as in “Who Wants to Be a Millionaire?”).
• The players / clients receive a question with the three possible answers, A B C, from the server at the same time.
• The player / client with the fastest correct answer gets one point.
• Every player / client is informed about the current game status.
• No Java Remote Method Invocation (RMI) or third-party libraries may be used, with the exception of JUnit.


Full Documentation is found in Documentation.pdf.
