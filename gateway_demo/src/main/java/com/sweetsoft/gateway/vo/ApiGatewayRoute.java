package com.sweetsoft.gateway.vo;

import lombok.Data;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.List;
import java.util.Map;

@Data
public class ApiGatewayRoute {
    //路由id
    private String id;
    private String uri;
    /**
     * predicates 参数的json字符串
     */
    private String predicatesJsonArray;
    /**
     * filters 参数的json字符串
     */
    private String filtersJsonArray;
    private List<PredicateDefinition> predicates;
    private List<FilterDefinition> filters;
    private Map<String,Object> metadata;
    //所属应用，只有一条应用
    private String appServiceId;
    private String routeId;
    //排序
    private Integer order;
    //消息的id
    private String messageId;
    private String operatorTime;
    private String appServiceVersion    ;
    private String routeVersion ;
    private String machineLearningServiceId;
    private Integer machineLearningServiceType;
    /**
     * 预留测试参数，如果不为空，为true，则跳过所有的路由鉴权。
     */
    private Boolean superSkip ;
}
