package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Posts_Chapter10 {

	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		try {
			// 画面入力
			Scanner scanner = new Scanner(System.in);
			System.out.println("パスワードを入力してください");
			String passWord = scanner.next();

			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					passWord);

			System.out.println("データベース接続成功:com.mysql.cj.jcdbConnectionImpl@xxxxxxxx");
			System.out.println("レコード更新を実施します");

			// SQLクエリを準備
			statement = con.createStatement();
			String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5";
			// SQLクエリを実行（DBMSに送信）
			System.out.println("レコード更新:" + statement.toString());
			int rowCnt = statement.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが更新されました");

			// SQLクエリを準備
			statement = con.createStatement();
			sql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";

			//　SQLクエリを実行（DBMSに送信）
			ResultSet result = statement.executeQuery(sql);
			System.out.println("数学・英語の点数が高い順に並べ替えました");

			// SQLクエリの実行結果を抽出
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int score_math = result.getInt("score_math");
				int score_english = result.getInt("score_english");
				System.out.println(result.getRow()
						+ "件目：生徒ID＝" + id + "／氏名＝" + name + "／数学=" + score_math + "／英語=" + score_english);
			}

		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
}
