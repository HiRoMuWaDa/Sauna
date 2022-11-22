package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Review;
import com.example.ecommerce_a.domain.Sauna;
import com.example.ecommerce_a.domain.User;


@Repository
public class SaunasRepository {
	// DBに接続するための変数定義
	@Autowired
	private NamedParameterJdbcTemplate template;

	// ResultSetオブジェクトに格納された複数行分のデータをList<Sauna>変数にセットしてreturnする
	private static final ResultSetExtractor<List<Sauna>> SAUNA_RESULT_SET_EXTRACTOR = (rs) -> {
		// データを格納するための変数を宣言
		// なぜlinkedListか説明できるように確認する
		List<Sauna> saunaList = new ArrayList<>();
		
		// レビューを格納するためのList<Review>変数を宣言(値はnullをセットしておく)
		List<Review> reviewList = null;
		
		// 前の行の記事IDを退避しておく変数
		// なぜlong?⇒入ってくるレビューの数が多くなる可能性があるから？
		long beforeSaunaId = 0;
		
		while (rs.next()) {
			// 現在検索しているsaunasテーブルのIDを格納するための変数を宣言
			int nowSaunaId = rs.getInt("id");
			
			//現在の記事IDと前の記事IDが違う場合はSaunaオブジェクトを生成
			if (nowSaunaId != beforeSaunaId) {
				Sauna sauna = new Sauna();
				sauna.setId(nowSaunaId);
				sauna.setName(rs.getString("name"));
				sauna.setArea(rs.getString("area"));
				sauna.setPrice(rs.getInt("price"));
				sauna.setMaleSaunaRoomTemp(rs.getInt("male_sauna_room_temp"));
				sauna.setMaleWaterBath(rs.getInt("male_water_bath"));
				sauna.setFemaleSaunaRoomTemp(rs.getInt("female_sauna_room_temp"));
				sauna.setFemaleWaterBath(rs.getInt("female_water_bath"));
				sauna.setDescription(rs.getString("description"));
				sauna.setImagePath(rs.getString("image_path"));
				sauna.setUrl(rs.getString("url"));
				sauna.setDeleted(rs.getBoolean("deleted"));
				
				// レビューがあった際にsaunaオブジェクトのreviewListに格納するためのArrayListをセットしておく
				reviewList = new ArrayList<Review>();
				sauna.setReviewList(reviewList);
				
				saunaList.add(sauna);
			}
			
			// 施設詳細ページだけあってコメントがない場合はReviewオブジェクトは作らない
			// なぜここはInteger?
			if(rs.getInt("rev_id") != 0) {
				Review review = new Review();
				review.setId(rs.getInt("rev_id"));
				review.setName(rs.getString("rev_name"));
				review.setReview(rs.getString("rev_review"));
				review.setDeleted(rs.getBoolean("deleted"));
				
				// reviewをsaunaオブジェクト内にセットされているreviewListに直接追加する
				reviewList.add(review);
			}
			
			// 現在検索しているsaunasテーブルのidを前の記事にいれる退避idに格納する
			beforeSaunaId = nowSaunaId;
		}
		return saunaList;
	};

	/**
	 * レビューを含めたサウナ施設一覧の全件取得
	 * 価格の安い順に表示させる（あとで変更する）
	 * @return サウナ施設の全件取得
	 */
	public List<Sauna> findAll() {
		// DBの動作確認済み
		String sql = " SELECT s.id, s.name, s.area, s.price, s.male_sauna_room_temp, s.male_water_bath, "
				+ " s.female_sauna_room_temp, s.female_water_bath, s.description, s.image_path,"
				+ " s.url, s.review, s.deleted, rev.id rev_id, rev.name rev_name, rev.review rev_review, rev.saunas_id,"
				+ " rev.deleted rev_deleted  FROM saunas as s LEFT JOIN reviews as rev ON s.id = rev.saunas_id ORDER BY s.price";
		List<Sauna> saunaList = template.query(sql, SAUNA_RESULT_SET_EXTRACTOR);
		return saunaList;
	}
	
	/**
	 * 新規のサウナ施設を登録する
	 * @param sauna
	 * @return
	 */
	public Sauna insert(Sauna sauna) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(sauna);
		String sql = "insert into saunas (name, area, price, male_sauna_room_temp, male_water_bath"
				+ " female_sauna_room_temp, female_water_bath, description,_image_path, url) "
				+ " values (name=:name, area=:area, price=:price, male_sauna_room_temp=:maleSaunaRoomTemp, male_water_bath=:maleWaterBath"
				+ " female_sauna_room_temp=:femaleSaunaRoomTemp, female_water_bath=:femaleWaterBath, description=:description,_image_path=:imagePath, url=:url)";
		template.update(sql, param);
		return sauna;
	}
	
	/**
	 * レビュー削除
	 * @param saunasId
	 */
	public void delete(int id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "delete from reviews where id=:id";
		template.update(sql, param);
	}
	
	/**
	 * 詳細表示用のメソッド
	 * 該当idの施設情報を取得する
	 * @param id
	 * @return
	 */
	public Sauna findById(Integer id) {
		System.out.println("123ertyuikjhgfds");
		System.out.println(id);

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		String sql = " SELECT s.id, s.name, s.area, s.price, s.male_sauna_room_temp, s.male_water_bath, "
				+ " s.female_sauna_room_temp, s.female_water_bath, s.description, s.image_path,"
				+ " s.url, s.review, s.deleted, rev.id rev_id, rev.name rev_name, rev.review rev_review, rev.saunas_id,"
				+ " rev.deleted rev_deleted  FROM saunas as s LEFT JOIN reviews as rev ON s.id = rev.saunas_id WHERE s.id=:id ORDER BY rev.id DESC";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Sauna> saunaList = template.query(sql, param, SAUNA_RESULT_SET_EXTRACTOR);

		return saunaList.get(0);
	}
	
	/**
	 * 名前で曖昧検索し、該当のレコードを取得するメソッド
	 * @param name
	 * @return
	 */
	public List<Sauna> findByName(String name) {
		String sql = "SELECT s.id, s.name, s.area, s.price, s.male_sauna_room_temp, s.male_water_bath, "
				+ " s.female_sauna_room_temp, s.female_water_bath, s.description, s.image_path,"
				+ "	s.url, s.review, s.deleted, rev.id rev_id, rev.name rev_name, rev.review rev_review, rev.saunas_id,"
				+ "	rev.deleted rev_deleted  FROM saunas as s LEFT JOIN reviews as rev ON s.id = rev.saunas_id WHERE s.name like :name ORDER BY price";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		
		List<Sauna> saunaList = template.query(sql, param, SAUNA_RESULT_SET_EXTRACTOR);

		System.out.println("名前で曖昧検索：" + saunaList);
		
		return saunaList;
	}
	
	
	/**
	 * エリアで該当のレコードを取得するメソッド
	 * @param name
	 * @return
	 */
	public List<Sauna> findByArea(List<String> areaList) {
		String sql = "SELECT s.id, s.name, s.area, s.price, s.male_sauna_room_temp, s.male_water_bath, "
				+ "	 s.female_sauna_room_temp, s.female_water_bath, s.description, s.image_path,"
				+ "	 s.url, s.review, s.deleted, rev.id rev_id, rev.name rev_name, rev.review rev_review, rev.saunas_id,"
				+ "	 rev.deleted rev_deleted  FROM saunas as s LEFT JOIN reviews as rev ON s.id = rev.saunas_id WHERE s.area IN (:areas) ORDER BY s.price";
//		sql = "SELECT * FROM " + TABLE_NAME + " WHERE area='台東区' OR area='墨田区' ORDER BY price";
			
		SqlParameterSource param = new MapSqlParameterSource().addValue("areas", areaList);
		
		if (areaList.size() == 0) {
			return null;
		}
		
		List<Sauna> saunaList = template.query(sql, param, SAUNA_RESULT_SET_EXTRACTOR);
		
		return saunaList;
	}
	
	/**
	 *コメント後のポイントを更新し、そのポイントを取得するメソッド
	 * @param id point
	 * @return point
	 */
	public User updatePoint(User user) {
		String updateSpl = "UPDATE users SET point=:point WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(updateSpl, param);
		return user;
	}
	
}
