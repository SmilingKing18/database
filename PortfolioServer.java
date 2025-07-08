import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PortfolioServer {
    private static String readFile(String p) throws IOException {
        return Files.readString(Paths.get(p));
    }

    public static void main(String[] args) throws Exception {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "index.html";
            } else {
                path = path.substring(1);
            }
            try {
                byte[] bytes;
                if (path.endsWith(".html")) {
                    String html = readFile(path);
                    if (html.contains("{{HEADER}}")) {
                        html = html.replace("{{HEADER}}", readFile("header.html"));
                    }
                    if (html.contains("{{MODAL}}")) {
                        html = html.replace("{{MODAL}}", readFile("modal.html"));
                    }
                    bytes = html.getBytes(StandardCharsets.UTF_8);
                } else {
                    bytes = Files.readAllBytes(Paths.get(path));
                }
                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", contentType(path));
                headers.add("Cache-Control", "max-age=3600");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } catch (IOException e) {
                String msg = "File not found";
                exchange.sendResponseHeaders(404, msg.length());
                exchange.getResponseBody().write(msg.getBytes());
                exchange.getResponseBody().close();
            }
        });
        server.start();
        System.out.println("Server started on http://localhost:" + port);
    }

    private static String contentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        return "text/plain";
    }
}
