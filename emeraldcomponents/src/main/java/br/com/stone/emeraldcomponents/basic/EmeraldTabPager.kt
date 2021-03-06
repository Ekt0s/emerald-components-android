package br.com.stone.emeraldcomponents.basic

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import br.com.stone.emeraldcomponents.R
import br.com.stone.emeraldcomponents.extension.getActivity
import kotlinx.android.synthetic.main.widget_tab_pager.view.*

/**
 * Created by renan.silva on 18/04/2018.
 */
class EmeraldTabPager : ConstraintLayout {

    init {
        inflate(context, R.layout.widget_tab_pager, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setAdapter(itemList: List<EmeraldTabItem>) {
        emeraldTabLayout.setupWithViewPager(emeraldViewPager)
        emeraldViewPager.adapter = EmeraldPagerAdapter(getActivity(), itemList)
        itemList.forEachIndexed { position, item ->
            item.iconId?.let { emeraldTabLayout.getTabAt(position)?.setIcon(it) }
        }
    }
}