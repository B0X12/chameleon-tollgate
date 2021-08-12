package com.chameleon.tollgate.fcm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chameleon.tollgate.fcm.exception.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.BatchResponse;

public class FCMSender {
	private static final String PACKAGE = "com.chameleon.tollgate";
	private static final String CREDENTIAL_PATH = "..\\tollgate-admin.json";
	private static final String CHANNEL_ID = "tollgate_fcm";
	
	private static FirebaseApp app = null;
	private static FirebaseMessaging messenger;
	
	public FCMSender() throws FileNotFoundException, IOException {
		if(FCMSender.app == null) {
			FCMSender.app = FirebaseApp.initializeApp(
					FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(FCMSender.CREDENTIAL_PATH)))
					.build(),
					"Tollgate");
			messenger = FirebaseMessaging.getInstance(app);
		}
	}
	
	public String send(Message message) throws FirebaseMessagingException {
		return messenger.send(message);
	}
	
	public BatchResponse sendAll(List<Message> messages) throws FirebaseMessagingException {
		return messenger.sendAll(messages);
	}
	
	public static MsgBuilder msgBuilder() {
		return new MsgBuilder();
	}
	
	
	public static class MsgBuilder {
		private String title;
		private String body;
		private Map<String, String> data;
		private String token;
		private String click_action;
		
		public MsgBuilder() {
			title = null;
			body = null;
			data = new HashMap<String, String>();
			token = null;
			click_action = null;
		}
		
		public MsgBuilder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public MsgBuilder setBody(String body) {
			this.body = body;
			return this;
		}
		
		public MsgBuilder setData(Map<String, String> data) {
			this.data = data;
			return this;
		}
		
		public MsgBuilder putData(String key, String value) {
			this.data.put(key, value);
			return this;
		}
		
		public MsgBuilder putAllData(Map<String, String> data) {
			this.data.putAll(data);
			return this;
		}
		
		public MsgBuilder setToken(String token) {
			this.token = token;
			return this;
		}
		
		public MsgBuilder setClickAction(String click_action) {
			this.click_action = click_action;
			return this;
		}
		
		public Message build() {
			if(title == null)
				throw(new MessageBuildException(FCMError.NO_TITLE));
			else if(body == null)
				throw(new MessageBuildException(FCMError.NO_BODY));
			else if(token == null)
				throw(new MessageBuildException(FCMError.NO_TOKEN));
			
			this.data.put("title", title);
			this.data.put("body", body);
			
			AndroidNotification.Builder andNotiBuilder = AndroidNotification.builder();
			andNotiBuilder.setChannelId(FCMSender.CHANNEL_ID)
				.setTitle(title)
				.setBody(body)
				.setImage("");
			if(this.click_action != null)
				andNotiBuilder.setClickAction(this.click_action);
			
			AndroidConfig.Builder andBuilder = AndroidConfig.builder();
			andBuilder.setRestrictedPackageName(FCMSender.PACKAGE)
				.setPriority(Priority.HIGH)
				.setNotification(andNotiBuilder.build())
				.putAllData(data);
			
			return Message.builder()
					.setToken(token)
					.setAndroidConfig(andBuilder.build())
					.build();
		}
	}

}
