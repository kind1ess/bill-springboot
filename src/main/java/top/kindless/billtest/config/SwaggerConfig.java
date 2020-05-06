package top.kindless.billtest.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.kindless.billtest.config.properties.KindlessProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final KindlessProperties kindlessProperties;

    private final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder().code(200).message("ok").build(),
            new ResponseMessageBuilder().code(400).message("Bad request").build(),
            new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
            new ResponseMessageBuilder().code(403).message("Forbidden").build(),
            new ResponseMessageBuilder().code(404).message("Not found").build(),
            new ResponseMessageBuilder().code(500).message("Internal server error").build());

    public SwaggerConfig(KindlessProperties kindlessProperties) {
        this.kindlessProperties = kindlessProperties;
    }

    @Bean
    public Docket docket(){
//        ParameterBuilder builder = new ParameterBuilder();
//        List<Parameter> parameters = new ArrayList<>();
//        builder.name("Authorization")
//                .description("验证令牌")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//        parameters.add(builder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo(
                        "自行车租赁系统api文档",
                        "自行车租赁系统api文档",
                        "1.0",
                        "kind1ess.github.io",
                        new Contact("kindless","https://kind1ess.github.io","2411347677@qq.com"),
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        new ArrayList()))
                .enable(kindlessProperties.isEnableSwagger())
                .globalResponseMessage(RequestMethod.GET,globalResponses)
                .globalResponseMessage(RequestMethod.POST,globalResponses)
                .globalResponseMessage(RequestMethod.PUT,globalResponses)
                .globalResponseMessage(RequestMethod.DELETE,globalResponses)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.kindless.billtest.controller"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }

    private List<SecurityContext> securityContexts(){
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
          SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*"))
                .build()
        );
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope[] authorizationScopes =  {new AuthorizationScope("global","accessEverything")};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization",authorizationScopes));
        return securityReferences;
    }
}
