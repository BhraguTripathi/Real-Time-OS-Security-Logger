Real-Time OS Security Event Logger
This project is a Java-based application designed to monitor a specified directory for file creation and deletion events in real-time. It logs these events to a file and displays them in a graphical user interface (GUI). The application utilizes Java's WatchService API for directory monitoring and Swing for the GUI.​

Features
Real-Time Monitoring: Watches a specified directory and its subdirectories for file creation and deletion events.​

Event Logging: Logs events with timestamps to a file named security_events.log.​

Graphical User Interface: Displays logged events in a GUI for easy viewing.​

Prerequisites
Java Development Kit (JDK): Ensure that JDK 8 or higher is installed on your system.​

Installation
Clone the Repository:

bash
Copy
Edit
git clone https://github.com/yourusername/RealTimeOSSecurityEventLogger.git
Navigate to the Project Directory:

bash
Copy
Edit
cd RealTimeOSSecurityEventLogger
Compile the Source Code:

bash
Copy
Edit
javac RealTimeOSSecurityEventLogger.java
Usage
Run the Application:

bash
Copy
Edit
java RealTimeOSSecurityEventLogger
Monitor Directory: By default, the application monitors the current directory ("."). To monitor a different directory, modify the startDirectoryMonitor method in the source code to specify the desired path.

View Logged Events:

GUI: The application window displays real-time logs of file creation and deletion events.​

Log File: All events are appended to security_events.log in the monitored directory.​

Configuration
Changing the Monitored Directory: To monitor a different directory, locate the following line in the main method:​

java
Copy
Edit
  startDirectoryMonitor(Paths.get("."));
Replace "." with the path to your desired directory. For example:​

java
Copy
Edit
  startDirectoryMonitor(Paths.get("C:/Users/YourUsername/Documents"));
Log File Location: By default, the log file security_events.log is created in the monitored directory. To change its location, modify the LOG_FILE constant:​

java
Copy
Edit
  private static final String LOG_FILE = "C:/path/to/your/logfile.log";
Troubleshooting
Permission Issues: Ensure that the application has the necessary permissions to access and monitor the specified directories.​

Platform Compatibility: The WatchService API is platform-dependent. While it works on most operating systems, behavior may vary. Refer to the official documentation for more details.​
Oracle Documentation
+1
Stack Overflow
+1

Contributing
Contributions are welcome! If you have suggestions or improvements, please fork the repository and submit a pull request.​

License
This project is licensed under the MIT License. See the LICENSE file for details.​

Acknowledgments
This application leverages Java's WatchService API for directory monitoring and Swing for the GUI. For more information on these technologies, refer to the official Java tutorials.
