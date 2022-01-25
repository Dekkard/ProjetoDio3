package dio.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class DBInnit extends AbstractHttpSessionApplicationInitializer {

	public DBInnit() {
		super(Config.class);
	}

}
