import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RealTimeOSSecurityEventLogger {

    private static final String LOG_FILE = "security_events.log";
    private static final BlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
    private static JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RealTimeOSSecurityEventLogger::createAndShowGUI);
        startDirectoryMonitor(Paths.get(".")); // Monitor the current directory
        processEvents();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Real-Time OS Security Event Logger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void startDirectoryMonitor(Path path) {
        new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                registerAllDirectories(path, watchService);
                while (true) {
                    WatchKey key = watchService.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    for (WatchEvent<?> event : events) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }
                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();
                        Path child = path.resolve(fileName);
                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            logEvent("CREATED", child.toString());
                            if (Files.isDirectory(child)) {
                                registerAllDirectories(child, watchService);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            logEvent("DELETED", child.toString());
                        }
                    }
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

    private static void registerAllDirectories(Path start, WatchService watchService) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void logEvent(String eventType, String filePath) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String event = String.format("%s | %s | %s", timestamp, eventType, filePath);
        eventQueue.offer(event);
        try {
            Files.writeString(Paths.get(LOG_FILE), event + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processEvents() {
        new Thread(() -> {
            while (true) {
                try {
                    String event = eventQueue.take();
                    SwingUtilities.invokeLater(() -> textArea.append(event + "\n"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}
