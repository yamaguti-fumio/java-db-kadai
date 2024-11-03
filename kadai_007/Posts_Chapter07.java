package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		PreparedStatement PreparedStatement = null;

		Object[][] postsList = {
				{ 1003, "2023-02-08", "昨日の夜は徹夜でした・・", 13 },
				{ 1002, "2023-02-08", "お疲れ様です！", 12 },
				{ 1003, "2023-02-09", "今日も頑張ります！", 18 },
				{ 1001, "2023-02-09", "無理は禁物ですよ！", 17 },
				{ 1002, "2023-02-10", "明日から連休ですね！", 20 }
		};

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
			System.out.println("レコード追加を実施します");

			// SQLクエリを準備
			String sql = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)";
			PreparedStatement = con.prepareStatement(sql);

			int rowCnt = 0;
			for (int i = 0; i < postsList.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換え
				PreparedStatement.setInt(1, (int) postsList[i][0]);
				PreparedStatement.setString(2, (String) postsList[i][1]);
				PreparedStatement.setString(3, (String) postsList[i][2]);
				PreparedStatement.setInt(4, (int) postsList[i][3]);
				rowCnt += PreparedStatement.executeUpdate();

			}
			System.out.println(rowCnt + "件のレコードが追加されました");
			System.out.println("ユーザIDが1002のレコードを検索しました");

			// SQLクエリを準備
			statement = con.createStatement();
			sql = "SELECT * FROM posts WHERE user_id = 1002;";

			//　SQLクエリを実行（DBMSに送信）
			ResultSet result = statement.executeQuery(sql);

			// SQLクエリの実行結果を抽出
			while (result.next()) {
				String posted_at = result.getString("posted_at");
				String post_content = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow()
						+ "件目：投稿日時＝" + posted_at + "／投稿内容=" + post_content + "／いいね数=" + likes);
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
