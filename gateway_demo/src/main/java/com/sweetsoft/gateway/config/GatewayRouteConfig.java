package com.sweetsoft.gateway.config;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sweetsoft.gateway.vo.ApiGatewayRoute;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        List<ApiGatewayRoute> routes = initRoutes();
        RouteLocatorBuilder.Builder routeBuilder = builder.routes();
        try {
            for (ApiGatewayRoute route : routes) {
                routeBuilder.route(route.getId(), r -> {
                    // 解析 predicates
                    JSONArray predicates = JSONUtil.parseArray(route.getPredicatesJsonArray());
                    String pathPattern = predicates.stream()
                            .filter(p -> "Path".equals(((JSONObject)p).getStr("name")))
                            .findFirst()
                            .map(p -> ((JSONObject)p).getJSONObject("args").getStr("pattern"))
                            .orElse("");

                    // 解析 filters
                    JSONArray filters = JSONUtil.parseArray(route.getFiltersJsonArray());
                    String rewritePattern = filters.stream()
                            .filter(f -> "RewritePath".equals(((JSONObject)f).getStr("name")))
                            .findFirst()
                            .map(f -> ((JSONObject)f).getJSONObject("args").getStr("regexp"))
                            .orElse("");

                    String replacement = filters.stream()
                            .filter(f -> "RewritePath".equals(((JSONObject)f).getStr("name")))
                            .findFirst()
                            .map(f -> ((JSONObject)f).getJSONObject("args").getStr("replacement"))
                            .orElse("");

                    return r.path(pathPattern)
                            .filters(f -> f.rewritePath(rewritePattern, replacement))
                            .uri(route.getUri());
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return routeBuilder.build();
    }

    private List<ApiGatewayRoute> initRoutes() {
        List<ApiGatewayRoute> routes = new ArrayList<>();
        
        // 配置 STOMP 路由
        ApiGatewayRoute stompRoute = new ApiGatewayRoute();
        stompRoute.setId("gateway_api_stomp_route");
        stompRoute.setUri("http://localhost:8080");
        stompRoute.setOrder(1);
        stompRoute.setPredicatesJsonArray("[{\"name\":\"Path\",\"args\":{\"pattern\":\"/gateway_stomp_api/**\"}}]");
        stompRoute.setFiltersJsonArray("[{\"name\":\"RewritePath\",\"args\":{\"regexp\":\"/gateway_stomp_api/(?<segment>.*)\",\"replacement\":\"/${segment}\"}}]");
        routes.add(stompRoute);

        // 配置 WebSocket 路由
        ApiGatewayRoute wsRoute = new ApiGatewayRoute();
        wsRoute.setId("gateway_ws_api_route");
        wsRoute.setUri("ws://localhost:8081");
        wsRoute.setOrder(2);
        wsRoute.setPredicatesJsonArray("[{\"name\":\"Path\",\"args\":{\"pattern\":\"/gateway_ws_api/**\"}}]");
        wsRoute.setFiltersJsonArray("[{\"name\":\"RewritePath\",\"args\":{\"regexp\":\"/gateway_ws_api/(?<segment>.*)\",\"replacement\":\"/${segment}\"}}]");
        routes.add(wsRoute);

        return routes;
    }
} 