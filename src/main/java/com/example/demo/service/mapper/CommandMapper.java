package com.example.demo.service.mapper;

import com.example.demo.service.dto.CommandParams;

/**
 * Маппер для команды
 */
public interface CommandMapper {

    CommandParams toCommandParams(String command) throws IllegalArgumentException;
}