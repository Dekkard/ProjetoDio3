package dio.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

public class ConnectionConfig{
	
	public static Connection Connect() {
		try {
			Properties properties = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test").getClientInfo();
			Enumeration<?> propertyNames = properties.propertyNames();
			while(propertyNames.hasMoreElements()) {
				System.out.println(propertyNames.nextElement());
			}
			if(properties.getProperty("user").isEmpty()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					System.out.print("Usuário: ");
					properties.setProperty("user", br.readLine());

					System.out.print("Senha: ");
					properties.setProperty("password", br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("!Conexão feita com sucesso!");
			return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", properties);
		} catch (SQLException e) {
			System.out.println("!Conexão falhou!");
			e.printStackTrace();
		}
		return null;
	}
}
