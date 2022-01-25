package dio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		Connection conn = null;
		String user = "", pw = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				while(user.isEmpty()) {
					System.out.print("Usuário: ");
					user = br.readLine();
				}
				System.out.print("Senha: ");
				pw = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", user, pw);
//			conn = ConnectionConfig.Connect();
		} catch (SQLException e) {
			System.out.println("!Conexão falhou!");
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM PESSOA");
			rs =  ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			for(int i=1;i<=col;i++) {
				System.out.print(rsmd.getColumnLabel(i)+"\t");
			}
			System.out.println();
			while(rs.next()) {
				for(int i=1;i<=col;i++) {
					System.out.print(rs.getString(i)+"\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			try {
				System.out.println(new BufferedReader(new InputStreamReader(System.in)).readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Conexão não pode ser fechada.");
		}
	}
}
