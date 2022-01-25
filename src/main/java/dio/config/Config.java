package dio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableJdbcHttpSession
public class Config {
//	private static final ModelMapper ModelMapper = null;
	@Value("${spring.datasource.url")
	String url;
	@Value("${spring.datasource.username")
	String user;
	@Value("${spring.datasource.password")
	String pass;
	@Bean
    @ConfigurationProperties("spring.datasource")
	public EmbeddedDatabase dataSource() {
		return new EmbeddedDatabaseBuilder().setName("test").setType(EmbeddedDatabaseType.H2)
				.addScript("org/springframework/session/jdbc/schema-h2.sql").build();
	}
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
//	@Bean
//	public Pessoa setPessoa() {
//		return new Pessoa();
//	}

//	@Bean
//	public ModelMapper setModelMapper() {
//		return ModelMapper;
//	}
}