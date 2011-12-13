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
package com.socialize.ui.profile.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.socialize.Socialize;
import com.socialize.android.ioc.IBeanFactory;
import com.socialize.entity.ListResult;
import com.socialize.entity.SocializeAction;
import com.socialize.error.SocializeException;
import com.socialize.listener.activity.UserActivityListListener;
import com.socialize.ui.view.LoadingItemView;
import com.socialize.view.BaseView;

/**
 * Renders a list of recent user activity.
 * @author Jason Polites
 *
 */
public class UserActivityView extends BaseView {

	// Local
	private LoadingItemView<UserActivityListItem> listView;
	private UserActivityListItemBuilder userActivityListItemBuilder;
	
	// Injected
	private int numItems = 20;
	
	private IBeanFactory<LoadingItemView<UserActivityListItem>> loadingItemViewFactory;
	
	public UserActivityView(Context context) {
		super(context);
	}

	public void init() {
		listView = loadingItemViewFactory.getBean();
		listView.setEmptyText("No recent activity");
		addView(listView);
	}
	
	public void loadUserActivity(long userId) {
		listView.showLoading();
		Socialize.getSocialize().listActivityByUser(userId, 0, numItems, new UserActivityListListener() {
			@Override
			public void onList(ListResult<SocializeAction> entities) {
				if(entities != null) {
					
					Date now = new Date();
					
					List<SocializeAction> items = entities.getItems();
					
					if(items != null && items.size() > 0) {
						ArrayList<UserActivityListItem> views = new ArrayList<UserActivityListItem>(items.size());
						
						for (SocializeAction item : items) {
							views.add(userActivityListItemBuilder.build(getActivity(), item, now));
						}
						
						listView.setItems(views);
						listView.showList();
					}
					else {
						listView.clear();
						listView.showEmptyText();
					}
				}
				else {
					listView.clear();
					listView.showEmptyText();
				}
			}
			@Override
			public void onError(SocializeException error) {
				listView.clear();
				listView.showEmptyText();
			}
		});
	}
	
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public void setLoadingItemViewFactory(IBeanFactory<LoadingItemView<UserActivityListItem>> loadingListViewFactory) {
		this.loadingItemViewFactory = loadingListViewFactory;
	}

	public void setUserActivityListItemBuilder(UserActivityListItemBuilder userActivityListItemBuilder) {
		this.userActivityListItemBuilder = userActivityListItemBuilder;
	}
}
