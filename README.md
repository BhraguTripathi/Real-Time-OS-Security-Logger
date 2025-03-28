# Real-Time OS Security Event Logger <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" align="right">

<p align="center">
  <img src="https://img.shields.io/badge/license-MIT-blue" alt="License">
  <img src="https://img.shields.io/badge/version-1.0.0-green" alt="Version">
  <img src="https://img.shields.io/badge/platform-Windows%20%7C%20Linux%20%7C%20MacOS-lightgrey" alt="Platform">
</p>

<div align="center">
  <img src="https://github.com/yourusername/RealTimeOSSecurityEventLogger/raw/main/screenshots/demo.gif" width="600" alt="Application Demo">
</div>

## 🚀 Features
<table>
  <tr>
    <td><strong>Real-Time Monitoring</strong></td>
    <td>▹</td>
    <td>Track file events in directory/subdirectories</td>
  </tr>
  <tr>
    <td><strong>Event Logging</strong></td>
    <td>▹</td>
    <td>Timestamped logs in security_events.log</td>
  </tr>
  <tr>
    <td><strong>GUI Interface</strong></td>
    <td>▹</td>
    <td>Swing-based real-time display</td>
  </tr>
</table>

## 📦 Installation
```bash
# Clone repository
git clone https://github.com/yourusername/RealTimeOSSecurityEventLogger.git

# Navigate to directory
cd RealTimeOSSecurityEventLogger

# Compile source
javac RealTimeOSSecurityEventLogger.java

🖥️ Usage

java RealTimeOSSecurityEventLogger

<div class="highlight"> <strong>Pro Tip:</strong> Modify these lines to customize: ```java // In RealTimeOSSecurityEventLogger.java startDirectoryMonitor(Paths.get(".")); // ← Change monitored directory private static final String LOG_FILE = "security_events.log"; // ← Custom log path ``` </div>

<div class="highlight"> <strong>Pro Tip:</strong> Modify these lines to customize: ```java // In RealTimeOSSecurityEventLogger.java startDirectoryMonitor(Paths.get(".")); // ← Change monitored directory private static final String LOG_FILE = "security_events.log"; // ← Custom log path ``` </div>
