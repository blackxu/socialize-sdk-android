/*
 * Copyright (c) 2011 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize;

import java.lang.reflect.Proxy;
import android.app.Activity;
import com.socialize.api.action.share.ShareUtilsProxy;
import com.socialize.entity.Entity;
import com.socialize.entity.User;
import com.socialize.listener.share.ShareAddListener;
import com.socialize.listener.share.ShareGetListener;
import com.socialize.listener.share.ShareListListener;
import com.socialize.networks.SocialNetworkListener;


/**
 * @author Jason Polites
 *
 */
public class ShareUtils {
	
	
	static ShareUtilsProxy proxy;
	
	static {
		proxy = (ShareUtilsProxy) Proxy.newProxyInstance(
				ShareUtilsProxy.class.getClassLoader(),
				new Class[]{ShareUtilsProxy.class},
				new SocializeActionProxy("shareUtils")); // Bean name
	}
	
	public static void showShareDialog (Activity context, Entity e, ShareAddListener listener) {
		proxy.showShareDialog(context, e, listener);
	};
	
	public static void shareViaEmail(Activity context, Entity e, ShareAddListener listener) {
		proxy.shareViaEmail(context, e, listener);
	};
	
	public static void shareViaSMS(Activity context, Entity e, ShareAddListener listener) {
		proxy.shareViaSMS(context, e, listener);
	};

	public static void shareViaSocialNetworks(Activity context, Entity e, SocialNetworkListener listener) {
		proxy.shareViaSocialNetworks(context, e, listener);
	};
	
	public static void getShare (Activity context, ShareGetListener listener, long id) {
		proxy.getShare(context, listener, id);
	};
	
	public static void getShares (Activity context, ShareListListener listener, long...ids) {
		proxy.getShares(context, listener, ids);
	};
	
	public static void getSharesByUser (Activity context, User user, int start, int end, ShareListListener listener) {
		proxy.getSharesByUser(context, user, start, end, listener);
	};
	
	public static void getSharesByEntity (Activity context, Entity e, int start, int end, ShareListListener listener) {
		proxy.getSharesByEntity(context, e, start, end, listener);
	};
	
	public static boolean canShareViaEmail(Activity context) {
		return proxy.canShareViaEmail(context);
	};
	
	public static boolean canShareViaSMS(Activity context) {
		return proxy.canShareViaSMS(context);
	};
}