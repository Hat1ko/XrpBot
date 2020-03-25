package com.hatiko.ripple.telegram.bot.core.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class TelegramChat {

	@Id
	private Long id;
	private LocalDateTime creationDate;
	private Boolean userChat;
	private Boolean groupChat;
	private Boolean channelChat;
	private Boolean superGroupChat;
	
	@ManyToOne
	TelegramUser user;
}
