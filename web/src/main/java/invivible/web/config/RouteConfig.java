//package invivible.web.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ConversionServiceFactoryBean;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.web.reactive.config.EnableWebFlux;
//
///**
// * Project:        In_Visible
// * <p>
// * Author:         Moritz Thomas
// * <p>
// * Creation date:  14.01.2020
// * <p>
// * <p/>
// */
//@Configuration
//public class RouteConfig {
//
//  @Bean(name="conversionService")
//  public ConversionServiceFactoryBean getConversionService() {
//    return new ConversionServiceFactoryBean();
//  }
//
//  @Bean
//  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//    return builder.routes()
//        .route(p -> p
//          .path("/database/**")
////        .filters()
//            .uri("http://localhost:8182")
//        )
//        .build();
//  }
//}
