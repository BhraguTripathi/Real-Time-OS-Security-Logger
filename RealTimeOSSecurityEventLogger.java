import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for layout
import java.io.IOException; // Importing IOException for handling IO errors
import java.nio.file.*; // Importing NIO file package for file and directory operations
import java.nio.file.attribute.BasicFileAttributes; // Importing for file attribute operations
import java.time.LocalDateTime; // Importing for handling date and time
import java.time.format.DateTimeFormatter; // Importing for formatting date and time
import java.util.List; // Importing List interface
import java.util.concurrent.BlockingQueue; // Importing BlockingQueue interface
import java.util.concurrent.LinkedBlockingQueue; // Importing LinkedBlockingQueue implementation

public class RealTimeOSSecurityEventLogger {

    // Path to the log file where events will be recorded
    private static final String LOG_FILE = "security_events.log";
    // Queue to hold events before they are processed
    private static final BlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
    // Text area in the GUI to display events
    private static JTextArea textArea;

    public static void main(String[] args) {
        // Initialize and display the GUI
        SwingUtilities.invokeLater(RealTimeOSSecurityEventLogger::createAndShowGUI);
        // Start monitoring the current directory
        startDirectoryMonitor(Paths.get("."));
        // Start processing events from the queue
        processEvents();
    }

    // Method to create and display the GUI
    private static void createAndShowGUI() {
        // Create a new window with the title
        JFrame frame = new JFrame("Real-Time OS Security Event Logger");
        // Set the default close operation to exit the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the window
        frame.setSize(600, 400);

        // Initialize the text area
        textArea = new JTextArea();
        // Make the text area non-editable
        textArea.setEditable(false);
        // Add the text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        // Add the scroll pane to the window
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the window visible
        frame.setVisible(true);
    }

    // Method to start monitoring a directory
    private static void startDirectoryMonitor(Path path) {
        new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                // Register the directory and all its subdirectories
                registerAllDirectories(path, watchService);
                while (true) {
                    // Wait for a watch key to be signaled
                    WatchKey key = watchService.take();
                    // Retrieve and process all pending events for the watch key
                    List<WatchEvent<?>> events = key.pollEvents();
                    for (WatchEvent<?> event : events) {
                        WatchEvent.Kind<?> kind = event.kind();
                        // Skip if the event is an overflow
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }
                        // Retrieve the file name from the event context
                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();
                        // Resolve the file path
                        Path child = path.resolve(fileName);
                        // Log the event based on its kind
                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            logEvent("CREATED", child.toString());
                            // If a directory is created, register it and its subdirectories
                            if (Files.isDirectory(child)) {
                                registerAllDirectories(child, watchService);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            logEvent("DELETED", child.toString());
                        }
                    }
                    // Reset the key and remove from the set if it's no longer valid
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Method to register a directory and all its subdirectories with the watch service
    private static void registerAllDirectories(Path start, WatchService watchService) throws IOException {
        // Walk the file tree starting from the given directory
        Files.walkFileTree(start, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // Register the directory with the watch service
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // Method to log events
    private static void logEvent(String eventType, String filePath) {
        // Get the current timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Format the event string
        String event = String.format("%s | %s | %s", timestamp, eventType, filePath);
        // Add the event to the queue
        eventQueue.offer(event);
        try {
            // Write the event to the log file
            Files.writeString(Paths.get(LOG_FILE), event + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to process events from the queue and update the GUI
    private static void processEvents() {
        new Thread(() -> {
            while (true) {
                try {
                    // Take an event from the queue
                    String event = eventQueue.take();
                    // Update the text area in the GUI
                    SwingUtilities.invokeLater(() -> textArea.append(event + "\n"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}
