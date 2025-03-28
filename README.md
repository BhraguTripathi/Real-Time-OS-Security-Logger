# Real-Time OS Security Event Logger 🔒

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey)

![Demo](https://github.com/yourusername/RealTimeOSSecurityEventLogger/raw/main/screenshots/demo.gif)

A real-time directory monitoring system that logs file creation/deletion events with timestamps and displays them in a GUI.

## ✨ Features
- ✅ **Real-Time File Monitoring** using Java WatchService API
- 📝 **Automatic Logging** to `security_events.log`
- 🖥️ **Swing GUI** for live event tracking
- ⏱️ Timestamped events (ISO 8601 format)
- 🔄 Recursive subdirectory monitoring

## 🚀 Getting Started

### Prerequisites
- JDK 17 or later
- Git (optional)

### Installation
```bash
# Clone the repository
git clone https://github.com/yourusername/RealTimeOSSecurityEventLogger.git

# Navigate to project directory
cd RealTimeOSSecurityEventLogger

# Compile the source
javac RealTimeOSSecurityEventLogger.java
```

### Usage
```bash
# Run the application (defaults to current directory)
java RealTimeOSSecurityEventLogger
```

**Custom Configuration**:
```java
// In RealTimeOSSecurityEventLogger.java

// Change monitored directory (line 15)
startDirectoryMonitor(Paths.get("/path/to/directory"));

// Change log file location (line 12)
private static final String LOG_FILE = "/custom/path/security_events.log";
```

## 📊 Configuration Overview
| Setting              | Default Value          | Customization Point           |
|----------------------|------------------------|--------------------------------|
| Monitored Directory  | Current Directory (.)  | `startDirectoryMonitor()` call |
| Log File Path        | security_events.log    | `LOG_FILE` constant            |
| Event Types Tracked  | CREATE, DELETE         | `StandardWatchEventKinds` enum |

## 🛠️ Troubleshooting

<details>
<summary><b>Common Issues</b></summary>

1. **No Events Detected**
   - Verify directory permissions
   - Check anti-virus/firewall settings
   - Ensure files aren't system/hidden

2. **Log File Not Created**
   - Check write permissions
   - Verify disk space
   - Ensure path is valid

3. **GUI Not Updating**
   - Confirm Swing dependencies
   - Check EDT thread status
</details>

## 🤝 Contributing

1. Fork the Project
2. Create your Feature Branch  
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit your Changes  
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push to the Branch  
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Open a Pull Request

## 📜 License
Distributed under MIT License - see [LICENSE](LICENSE) for details.

---

Made with ❤️ by Bhragu Tripathi

📚 **Documentation**:  
[Java WatchService Docs](https://docs.oracle.com/javase/8/docs/api/java/nio/file/WatchService.html) | 
[Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
