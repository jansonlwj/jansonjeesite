package com.thinkgem.jeesite.common.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.config.Global;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光插件
 * 
 * @author janson
 */
public class JgPushUtils {

	protected static Logger logger = LoggerFactory.getLogger(JgPushUtils.class);

	private static String masterSecret;
	private static String appKey;
	private static JPushClient jpushClient;

	public JgPushUtils() {
		appKey = Global.getConfig("jgappKey");
		masterSecret = Global.getConfig("jgmasterSecret");
		jpushClient = new JPushClient(masterSecret, appKey);
	}

	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().autoBadge().setAlert(alert).build())
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle("高技修车").setAlert(alert).build())
						.build())
				.build();
	}

	public static PushPayload buildPushObject_all_alias_alert(String aliase, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(new String[] { aliase }))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().autoBadge().setAlert(alert).build())
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle("高技修车").setAlert(alert).build())
						.build())
				.build();
	}

	public static PushPayload buildPushObject_IOS_all_alert(String alert, String msgType) {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(((IosNotification.Builder) IosNotification.newBuilder().setAlert(alert)
								.autoBadge().setSound("default").addExtra("notifyType", msgType)).build())
						.build())
				.build();
	}

	public static PushPayload buildPushObject_IOS_alise_alert(String alert, String msgType, List<String> aliases) {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(aliases))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(((IosNotification.Builder) IosNotification.newBuilder().setAlert(alert)
								.autoBadge().setSound("default").addExtra("notifyType", msgType)).build())
						.build())
				.build();
	}

	public static PushPayload buildPushObject_android_all_alertWithTitle(String alert, String msgType) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(((AndroidNotification.Builder) AndroidNotification.newBuilder()
								.setAlert(alert).setTitle("高技修车").addExtra("notifyType", msgType)).build())
						.build())
				.build();
	}

	public static String pushAllNotify(String alertMsg) {
		logger.debug("进入极光消息推送 PushAllNotify");

		PushPayload payload_all = buildPushObject_all_all_alert(alertMsg);
		String returnStr = "1";
		try {
			PushResult result = jpushClient.sendPush(payload_all);
			System.out.println("Got result - " + result);
			logger.debug("极光推送  返回结果：" + result);
		} catch (APIConnectionException e) {
			System.out.println("Connection error, should retry later - " + e.getMessage());
			logger.debug("极光推送  APIConnectionException：" + e.getMessage());
			returnStr = "-1";
		} catch (APIRequestException e) {
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			logger.debug("极光推送  APIRequestException：" + e.getErrorMessage());
			returnStr = "-1";
		}
		return returnStr;
	}

	public static String pushNotifyByAlise(String alises, String alertMsg) {
		PushPayload payload_all = buildPushObject_all_alias_alert(alises, alertMsg);
		String returnStr = "1";
		try {
			PushResult result = jpushClient.sendPush(payload_all);
			System.out.println("Got result - " + result);
		} catch (APIConnectionException e) {
			System.out.println("Connection error, should retry later - " + e.getMessage());
			returnStr = "-1";
		} catch (APIRequestException e) {
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			returnStr = "-1";
		}
		return returnStr;
	}

	public static void main(String[] args) {
		JgPushUtils jgPushUtils=new JgPushUtils();
		jgPushUtils.pushAllNotify("111");
	}
}
