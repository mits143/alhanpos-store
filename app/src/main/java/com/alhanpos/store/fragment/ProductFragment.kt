package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.alhanpos.store.R
import com.alhanpos.store.adapter.FragmentPagerAdapter
import com.alhanpos.store.databinding.FragmentProductBinding


class ProductFragment : BaseFragment<FragmentProductBinding>(), RadioGroup.OnCheckedChangeListener,
    ViewPager.OnPageChangeListener {

    private lateinit var adapter: FragmentPagerAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding =
        FragmentProductBinding::inflate

    private val args: ProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        adapter = FragmentPagerAdapter(
            childFragmentManager
        )
        adapter.add(AllFragment())
        adapter.add(CategoryFragment())
        adapter.add(BrandFragment())
        binding.radioGroup.setOnCheckedChangeListener(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener (this)
        binding.viewPager.currentItem = args.currentItem
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        // find which radio button is selected
        if (checkedId == R.id.rbAll)
            binding.viewPager.currentItem = 0
        else if (checkedId == R.id.rbCategory)
            binding.viewPager.currentItem = 1
        else
            binding.viewPager.currentItem = 2
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 0)
            binding.rbAll.isChecked = true
        else if (position == 1)
            binding.rbCategory.isChecked = true
        else
            binding.rbBrand.isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}