package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Sauna;
import com.example.ecommerce_a.repository.SaunasRepository;

@Service
public class SaunasService {
	@Autowired
	private SaunasRepository saunasRepository;

	/**
	 * SaunasRepositoryのfindAllを実行するメソッド
	 * 
	 * @return サウナの全件情報を取得
	 */
	public List<Sauna> showList() {
		List<Sauna> saunaList = saunasRepository.findAll();
		return saunaList;
	}

	/**
	 * SaunasRepositoryのfindByIdを実行するためのメソッド
	 * 
	 * @param id
	 * @return 該当の施設の詳細情報を取得する
	 */
	public Sauna showDetail(Integer id) {
		return saunasRepository.findById(id);
	}

	/**
	 * SaunasRepositoryのfindByNameを実行するメソッド 引数の値で曖昧検索できる
	 * 
	 * @param name
	 * @return
	 */
	public List<Sauna> searchByName(String name) {
		List<Sauna> saunaList = saunasRepository.findByName(name);
		return saunaList;
	}

	/**
	 * SaunasRepositoryのfindByAreaを実行するメソッド
	 * 
	 * @param area
	 * @return
	 */
	public List<Sauna> searchByArea(List<String> areaList) {
		List<Sauna> saunaList = saunasRepository.findByArea(areaList);
		return saunaList;
	}
	
	/**
	 * レビュー削除用のメソッド
	 * @param id
	 */
	public void deleteReview(int id) {
		saunasRepository.delete(id);
	}
}
