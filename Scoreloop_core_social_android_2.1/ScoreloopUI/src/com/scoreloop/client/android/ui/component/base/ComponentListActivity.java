/*
 * In derogation of the Scoreloop SDK - License Agreement concluded between
 * Licensor and Licensee, as defined therein, the following conditions shall
 * apply for the source code contained below, whereas apart from that the
 * Scoreloop SDK - License Agreement shall remain unaffected.
 * 
 * Copyright: Scoreloop AG, Germany (Licensor)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.scoreloop.client.android.ui.component.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.scoreloop.client.android.ui.R;
import com.scoreloop.client.android.ui.framework.BaseListAdapter;
import com.scoreloop.client.android.ui.framework.BaseListItem;
import com.scoreloop.client.android.ui.framework.BaseListAdapter.OnListItemClickListener;

public abstract class ComponentListActivity<T extends BaseListItem> extends ComponentActivity implements OnClickListener,
		OnItemClickListener, OnListItemClickListener<T> {

	private BaseListItem	_footerItem;

	@SuppressWarnings("unchecked")
	public BaseListAdapter<T> getBaseListAdapter() {
		return (BaseListAdapter<T>) getListAdapter();
	}

	public ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}

	public ListView getListView() {
		return (ListView) findViewById(R.id.sl_list);
	}

	public void hideFooter() {
		final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sl_footer);
		if (viewGroup != null) {
			_footerItem = null;
			viewGroup.removeAllViews();
		}
	}

	public void onClick(final View view) {
		if (_footerItem != null) {
			onFooterItemClick(_footerItem);
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sl_list_view, true);

		getListView().setFocusable(true);
		getListView().setOnItemClickListener(this);
	}

	protected void onFooterItemClick(final BaseListItem footerItem) {
		// intentionally empty - should be overridden in subclass
	}

	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		getBaseListAdapter().onItemClick(parent, view, position, id);
	}

	public void onListItemClick(final T item) {
		// intentionally empty - should be overridden in subclass
	}

	public void setListAdapter(final ListAdapter adapter) {
		getListView().setAdapter(adapter);
		getBaseListAdapter().setOnListItemClickListener(this);
	}

	public void showFooter(final BaseListItem footerItem) {
		final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sl_footer);
		if (viewGroup != null) {
			final View footerView = footerItem.getView(null, null);
			if (footerView != null) {
				_footerItem = footerItem;
				viewGroup.addView(footerView);
				if (footerItem.isEnabled()) {
					footerView.setOnClickListener(this);
				}
			}
		}
	}
}
