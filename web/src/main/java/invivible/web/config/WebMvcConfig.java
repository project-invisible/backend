package invivible.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  23.12.2019
 * <p>
 * <p/>
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }
}


