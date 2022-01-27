package com.example.IdentityProvider;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class IdetityProviderWS extends WsConfigurerAdapter {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcher(ApplicationContext applicationContext)
    {
        MessageDispatcherServlet result = new MessageDispatcherServlet();
        result.setApplicationContext(applicationContext);
        result.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(result, "/sample/*");
    }
    @Bean(name ="auth")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema calculatorSchema)
    {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();
        result.setPortTypeName("AuthPort");
        result.setLocationUri("/sample");
        result.setTargetNamespace("http://com.example.IdentityProvider/Authentication");
        result.setSchema(calculatorSchema);
        return result;
    }
    @Bean
    public XsdSchema calculatorSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("identityProvider.xsd"));
    }
}
