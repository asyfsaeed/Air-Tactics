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

package com.scoreloop.client.android.ui.component.score;

import android.view.View;
import android.widget.TextView;

import com.scoreloop.client.android.core.model.Ranking;
import com.scoreloop.client.android.core.model.Score;
import com.scoreloop.client.android.ui.R;
import com.scoreloop.client.android.ui.component.base.ComponentActivity;
import com.scoreloop.client.android.ui.component.base.Constant;

public class ScoreHighlightedListItem extends ScoreListItem {

	static class HighlightedViewHolder extends StandardViewHolder {
		TextView	percentText;
	}

	private static final int	ROUND_TO_PERCENT	= 5;

	private Ranking				_ranking;

	public ScoreHighlightedListItem(final ComponentActivity activity, final Score score, final Ranking ranking) {
		super(activity, score);
		_ranking = ranking;
	}

	@Override
	protected StandardViewHolder createViewHolder() {
		return new HighlightedViewHolder();
	}

	@Override
	protected void fillViewHolder(final View view, final StandardViewHolder holder) {
		super.fillViewHolder(view, holder);
		((HighlightedViewHolder) holder).percentText = (TextView) view.findViewById(R.id.sl_list_item_score_percent);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.sl_list_item_score_highlighted;
	}

	private String getPercentString() {
		if (_ranking == null) {
			return "";
		}
		final int total = _ranking.getTotal();
		if (total == 0) {
			return "";
		}
		final int percent = _ranking.getRank() * 100 / total;
		final int roundedPercent = (percent + ROUND_TO_PERCENT - 1) / ROUND_TO_PERCENT * ROUND_TO_PERCENT;
		return String.format(getContext().getString(R.string.sl_format_leaderboards_percent), roundedPercent);
	}

	@Override
	public int getType() {
		return Constant.LIST_ITEM_TYPE_SCORE_HIGHLIGHTED;
	}

	public void setRanking(final Ranking ranking) {
		_ranking = ranking;
	}

	@Override
	protected void updateViews(final StandardViewHolder holder) {
		super.updateViews(holder);
		((HighlightedViewHolder) holder).percentText.setText(getPercentString());
	}
}
