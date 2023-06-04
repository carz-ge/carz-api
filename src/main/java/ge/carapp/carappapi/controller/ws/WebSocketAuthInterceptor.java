//package ge.carapp.carappapi.controller.ws;
//
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Component
//@Slf4j
//public class WebSocketAuthInterceptor implements HandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
//                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
//        log.info("Before handshake: {}, {}, {}", request.getURI(), request.getHeaders(), attributes);
//        // Perform authentication here
//        // You can access the authentication details using SecurityContextHolder
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("userDetails: {}", authentication.getPrincipal());
//        // Store the authentication details in the WebSocket session attributes
//        attributes.put("authentication", authentication.getPrincipal());
//
//
//        // Return true to allow the handshake to continue
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        // Do nothing after the handshake
//    }
//}
