package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Option;
import com.example.ecommerce_a.repository.OptionRepository;

@Service
public class OptionService {

	@Autowired
	private OptionRepository optionRepository;

	public List<Option> findAll() {
		List<Option> optionList = optionRepository.findAll();
		return optionList;
	}
}
