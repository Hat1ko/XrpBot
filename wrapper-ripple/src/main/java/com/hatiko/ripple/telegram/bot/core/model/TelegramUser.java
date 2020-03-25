package com.hatiko.ripple.telegram.bot.core.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {

	@Id
	private Integer id;
	private LocalDateTime creationDate;
	private String userName;
	private Boolean bot;
	private String firstName;
	private String lastName;
	private String languageCode;
}
