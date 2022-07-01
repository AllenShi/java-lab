package net.sjl.spring;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {
	
	   @Bean
	    public Docket postsApi() {
	        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
	                .apiInfo(apiInfo()).select().paths(postPaths()).build();
	    }

	    private Predicate<String> postPaths() {
	        return or(regex("/api/*.*"));
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Spring demo API")
	                .description("Spring demo API")
	                .licenseUrl("Apache").version("1.0").build();
	    }
	    
	    /**
	     * http://localhost:8080/docs/swagger-ui.html to access swagger-ui
	     */
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	    	registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
	    	registry.addRedirectViewController("/docs/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
	    	registry.addRedirectViewController("/docs/swagger-resources/configuration/security","/swagger-resources/configuration/security");
	    	registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
	    }

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    	registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
	    }

}