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

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scoreloop.client.android.ui.R;
import com.scoreloop.client.android.ui.framework.BaseListItem;
import com.scoreloop.client.android.ui.util.ImageDownloader;

public class StandardListItem<T> extends BaseListItem {

	public static class StandardViewHolder {
		public ImageView	icon;
		public TextView		title;
		public TextView		subTitle;
		public TextView		subTitle2;
	}

	private StandardViewHolder	_holder;
	private String				_subTitel;
	private String				_subTitle2;
	private final T				_target;

	public StandardListItem(final ComponentActivity activity, final Drawable drawable, final String title, final String subTitle,
			final T target) {
		super(activity, drawable, title);
		_subTitel = subTitle;
		_target = target;
	}

	public StandardListItem(final ComponentActivity activity, final T target) {
		super(activity, null, null);
		_target = target;
	}

	protected StandardViewHolder createViewHolder() {
		return new StandardViewHolder();
	}

	protected void fillViewHolder(final View view, final StandardViewHolder holder) {
		final int iconId = getIconId();
		if (iconId != 0) {
			holder.icon = (ImageView) view.findViewById(iconId);
		}
		holder.title = (TextView) view.findViewById(getTitleId());
		final int subTitleId = getSubTitleId();
		if (subTitleId != 0) {
			holder.subTitle = (TextView) view.findViewById(subTitleId);
		}
		final int subTitle2Id = getSubTitle2Id();
		if (subTitle2Id != 0) {
			holder.subTitle2 = (TextView) view.findViewById(subTitle2Id);
		}
	}

	public ComponentActivity getComponentActivity() {
		return (ComponentActivity) getContext();
	}

	protected int getIconId() {
		return R.id.sl_icon;
	}

	protected String getImageUrl() {
		return null;
	}

	protected int getLayoutId() {
		return R.layout.sl_list_item_icon_title_subtitle;
	}

	public String getSubTitle() {
		return _subTitel;
	}

	public String getSubTitle2() {
		return _subTitle2;
	}

	public void setSubTitle2(String subTitle2) {
		this._subTitle2 = subTitle2;
	}

	protected int getSubTitleId() {
		return R.id.sl_subtitle;
	}

	protected int getSubTitle2Id() {
		return 0;
	}

	public T getTarget() {
		return _target;
	}

	protected int getTitleId() {
		return R.id.sl_title;
	}

	@Override
	public int getType() {
		return Constant.LIST_ITEM_TYPE_STANDARD;
	}

	@Override
	public View getView(View view, final ViewGroup parent) {
		if (view == null) {
			view = getLayoutInflater().inflate(getLayoutId(), null);
			_holder = createViewHolder();
			fillViewHolder(view, _holder);
			view.setTag(_holder);
		} else {
			_holder = (StandardViewHolder) view.getTag();
		}
		updateViews(_holder);
		return view;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setSubTitle(final String subTitle) {
		_subTitel = subTitle;
	}

	protected void updateViews(final StandardViewHolder holder) {
		final String imageUrl = getImageUrl();
		if (imageUrl != null) {
			Drawable drawable = getDrawableLoading();
			ImageDownloader.downloadImage(imageUrl, drawable, holder.icon);
		} else {
			Drawable drawable = getDrawable();
			if (drawable != null) {
				holder.icon.setImageDrawable(drawable);
			}
		}
		holder.title.setText(getTitle());
		final TextView subTitle = holder.subTitle;
		if (subTitle != null) {
			subTitle.setText(getSubTitle());
		}
		final TextView subTitle2 = holder.subTitle2;
		if (subTitle2 != null) {
			subTitle2.setText(getSubTitle2());
		}
	}

	protected Drawable getDrawableLoading() {
		return getContext().getResources().getDrawable(R.drawable.sl_icon_games_loading);
	}
}
