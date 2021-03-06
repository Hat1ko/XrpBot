package com.hatiko.ripple.telegram.bot.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "message_id")
public class MessageIdEntity {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "chat_id")
	private Long chatId;
	
	@Column(name = "last_deleted")
	private Integer lastDeleted = 0;
	@Column(name = "last_sent")
	private Integer lastSent = 0;
	
}
