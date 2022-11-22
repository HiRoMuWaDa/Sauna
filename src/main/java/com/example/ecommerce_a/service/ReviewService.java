package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Review;
import com.example.ecommerce_a.repository.ReviewRepository;

/**
 * コメント関連サービス.
 * 
 * @author nahatamanami
 *
 */
@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	/**
	 * コメントを登録します.
	 * 
	 * @param comment
	 *            コメント情報
	 * @return 登録したコメント情報
	 */
	public void save(Review review) {
		reviewRepository.insert(review);
	}
}
